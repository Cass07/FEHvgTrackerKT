package wiki.feh.api.fehvgalertapikotlin.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import wiki.feh.api.fehvgalertapikotlin.domain.global.dto.VgViewDto
import wiki.feh.api.fehvgalertapikotlin.domain.global.service.VgViewService
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsGetWithPicDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsListResponseDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsResponseDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsViewDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.service.PostsService
import wiki.feh.api.fehvgalertapikotlin.domain.posts.service.PostsViewService
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.service.VgDataService
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.service.VgInfoService
import wiki.feh.api.fehvgalertapikotlin.global.config.util.SecurityUtil
import wiki.feh.api.fehvgalertapikotlin.global.util.VgDataGetCron

@Controller
class indexController(
    val postsService: PostsService,
    val securityUtil: SecurityUtil,
    val postsViewService: PostsViewService,

    val vgDataService: VgDataService,
    val vgInfoService: VgInfoService,
    val vgViewService: VgViewService,

    val vgDataGetCron: VgDataGetCron
) {
    val custUri: String = "" //"/voting"

    //Board Controller

    @GetMapping("/admin/board/")
    fun index(model: Model): String {
        val postsViewDto: PostsViewDto = postsViewService.getPostsListView(1)

        model.addAttribute("header_title", "로그 게시판")
        model.addAttribute("customUri", custUri)
        model.addAttribute("posts", postsViewDto.postsList)
        model.addAttribute("userName", securityUtil.getLoginUser())
        postsViewDto.viewModel.forEach { (key, value) -> model.addAttribute(key, value) }

        return "index"
    }

    @GetMapping("/admin/board/page/{page}")
    fun indexPage(model: Model, @PathVariable page: Int): String {
        val postsViewDto: PostsViewDto = postsViewService.getPostsListView(page)

        model.addAttribute("header_title", "로그 게시판")
        model.addAttribute("customUri", custUri)
        model.addAttribute("posts", postsViewDto.postsList)
        model.addAttribute("userName", securityUtil.getLoginUser())
        postsViewDto.viewModel.forEach { (key, value) -> model.addAttribute(key, value) }

        return "index"
    }

    @GetMapping("/admin/board/posts/{id}")
    fun posts(model: Model, @PathVariable id: Long): String {
        val postsGetWithPicDto: PostsGetWithPicDto? = postsService.getByIdWithPic(id)

        model.addAttribute("header_title", "글 내용 보기")
        model.addAttribute("customUri", custUri)
        println(postsGetWithPicDto?.createDate)

        if (postsGetWithPicDto != null) {
            model.addAttribute("posts", postsGetWithPicDto)
            return "posts"
        } else {
            model.addAttribute("errorMessage", "삭제되었거나 조회되지 않는 게시물입니다.")
            return "posts-error"
        }
    }

    @GetMapping("/admin/board/posts/save")
    fun postsSave(model: Model): String {
        model.addAttribute("header_title", "글 등록")
        model.addAttribute("user", securityUtil.getLoginUser())
        model.addAttribute("customUri", custUri)
        return "posts-save"
    }

    @GetMapping("/admin/board/posts/update/{id}")
    fun postsUpdate(@PathVariable id: Long, model: Model): String {
        model.addAttribute("header_title", "글 수정하기")
        model.addAttribute("customUri", custUri)
        val dto: PostsResponseDto = postsService.findbyId(id)
        model.addAttribute("posts", dto)

        if (dto.author != securityUtil.getLoginUser()?.email) {
            model.addAttribute("errorMessage", "자신이 쓴 글만 수정 가능합니다")
            return "posts-error"
        }
        return "posts-update"
    }

    //Voting Gauntlet Controller

    @GetMapping("/admin/vginfo")
    fun setVginfo(model: Model): String {
        model.addAttribute("header_title", "투표대전 데이터 세팅");
        model.addAttribute("customUri", custUri);
        return "setting-vginfo";
    }

    @GetMapping("/vg/vgnum/{vgnum}/round/{round}/tournum/{tournum}")
    fun getVgDataDetail(model: Model, @PathVariable vgnum: Int, @PathVariable round: Int, @PathVariable tournum: Int): String {
        val vgInfoEntity: VgInfoGetDto? = vgInfoService.findbyVgnumber(vgnum)
        if (vgInfoEntity == null) {
            model.addAttribute("errorMessage", "해당 투표대전이 존재하지 않습니다.")
            return "posts-error"
        }
        val vgDataGetDtoList: VgDataGetDto? = vgDataService.getFirstVgDatabyNumRoundTour(vgnum, round, tournum)
        if (vgDataGetDtoList == null) {
            model.addAttribute("errorMessage", "해당 투표대전 라운드가 존재하지 않습니다.")
            return "posts-error"
        }

        val team1Index: Int = vgDataGetDtoList.team1Index
        val team2Index: Int = vgDataGetDtoList.team2Index

        val team1Name: String = vgInfoEntity.getTeamIdbyIndex(team1Index)?.split("#")?.get(1) ?: "없음"
        val team2Name: String = vgInfoEntity.getTeamIdbyIndex(team2Index)?.split("#")?.get(1) ?: "없음"

        model.addAttribute("header_title", "세부 데이터 : ${vgnum}회 " + vgInfoEntity.vgTitle + " ${round}라운드 : $team1Name vs $team2Name")

        model.addAttribute("vg_info", vgInfoEntity)
        model.addAttribute("team1_name", team1Name)
        model.addAttribute("team2_name", team2Name)
        model.addAttribute("vgNumber", vgnum)
        model.addAttribute("roundNumber", round)
        model.addAttribute("tourNumber", tournum)
        model.addAttribute("customUri", custUri)

        return "vg-data"
    }

    @GetMapping("/vg/")
    fun vgMain(model: Model): String {
        val vgViewDto: VgViewDto? = vgViewService.getVgMainbyid(-1L)
        if (vgViewDto == null) {
            model.addAttribute("errorMessage", "최신 투표대전이 존재하지 않습니다.")
            return "posts-error"
        }
        vgViewDto.viewModel.forEach { (t, u) -> model.addAttribute(t, u) }
        model.addAttribute("header_title", "투표대전 점수 트래커")
        model.addAttribute("title", "메인 페이지")
        model.addAttribute("customUri", custUri)
        model.addAttribute("vgInfo", vgViewDto.vgInfoEntity)
        model.addAttribute("currentRoundVgdata", vgViewDto.currentRoundVgdata)
        model.addAttribute("round1Vgdata", vgViewDto.round1Vgdata)
        model.addAttribute("round2Vgdata", vgViewDto.round2Vgdata)
        model.addAttribute("round3Vgdata", vgViewDto.round3Vgdata)

        return vgViewDto.viewString
    }

    @GetMapping("/vg/past/")
    fun vgPastList(model: Model) : String {
        val vgViewDto: VgViewDto? = vgViewService.getVgMainbyid(-1L)
        if (vgViewDto == null) {
            model.addAttribute("errorMessage", "최신 투표대전이 존재하지 않습니다.")
            return "posts-error"
        }
        vgViewDto.viewModel.forEach { (t, u) -> model.addAttribute(t, u) }
        model.addAttribute("header_title", "투표대전 점수 트래커")
        model.addAttribute("title", "메인 페이지")
        model.addAttribute("customUri", custUri)
        model.addAttribute("vgInfo", vgViewDto.vgInfoEntity)
        model.addAttribute("currentRoundVgdata", vgViewDto.currentRoundVgdata)
        model.addAttribute("round1Vgdata", vgViewDto.round1Vgdata)
        model.addAttribute("round2Vgdata", vgViewDto.round2Vgdata)
        model.addAttribute("round3Vgdata", vgViewDto.round3Vgdata)

        return if(vgViewDto.viewString == "posts-error")
            "posts-error"
        else
            "vg-data-all"
    }

    @GetMapping("/vg/past/{id}")
    fun vgPastListbyId(model:Model, @PathVariable id: Long) : String {
        val vgViewDto: VgViewDto? = vgViewService.getVgMainbyid(id)
        if (vgViewDto == null) {
            model.addAttribute("errorMessage", "해당 투표대전이 존재하지 않습니다.")
            return "posts-error"
        }
        vgViewDto.viewModel.forEach { (t, u) -> model.addAttribute(t, u) }
        model.addAttribute("header_title", "투표대전 점수 트래커")
        model.addAttribute("title", "메인 페이지")
        model.addAttribute("customUri", custUri)
        model.addAttribute("vgInfo", vgViewDto.vgInfoEntity)
        model.addAttribute("currentRoundVgdata", vgViewDto.currentRoundVgdata)
        model.addAttribute("round1Vgdata", vgViewDto.round1Vgdata)
        model.addAttribute("round2Vgdata", vgViewDto.round2Vgdata)
        model.addAttribute("round3Vgdata", vgViewDto.round3Vgdata)

        return if(vgViewDto.viewString == "posts-error")
            "posts-error"
        else
            "vg-data-all"
    }

    @GetMapping("/vg/first/")
    fun vgFirst(model: Model) : String {
        val vgViewDto: VgViewDto? = vgViewService.getVgFirstbyId(-1L)
        if (vgViewDto == null) {
            model.addAttribute("errorMessage", "최신 투표대전이 존재하지 않습니다.")
            return "posts-error"
        }
        vgViewDto.viewModel.forEach { (t, u) -> model.addAttribute(t, u) }
        model.addAttribute("header_title", "투표대전 점수 트래커")
        model.addAttribute("title", "5시 초동 데이터")
        model.addAttribute("customUri", custUri)
        model.addAttribute("vgInfo", vgViewDto.vgInfoEntity)
        model.addAttribute("currentRoundVgdata", vgViewDto.currentRoundVgdata)
        model.addAttribute("round1Vgdata", vgViewDto.round1Vgdata)
        model.addAttribute("round2Vgdata", vgViewDto.round2Vgdata)
        model.addAttribute("round3Vgdata", vgViewDto.round3Vgdata)

        return vgViewDto.viewString
    }

    @GetMapping("/admin/manualcron")
    fun vgCronManual() : String{
        vgDataGetCron.GetVgData()
        return "redirect:/admin/board/"
    }

    //test

    @GetMapping("/hello")
    fun getpost(): List<PostsListResponseDto> {
        println(securityUtil.getLoginUser()?.name)
        return postsService.findAllDecs()
    }

    @GetMapping("/hello2")
    fun getpost2(): String {
        return "index"
    }
}