package wiki.feh.api.fehvgalertapikotlin.domain.posts.service

import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsListResponseDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsViewDto

@Service
class PostsViewService(
    val postsService: PostsService
) {

    @Transactional(readOnly = true)
    fun getPostsListView(page: Int): PostsViewDto {
        var viewModel: HashMap<String, Any> = HashMap<String, Any>()
        val listPage: Page<PostsListResponseDto> = postsService.findAllDecsPage(page - 1)

        setModeltoPagenation(viewModel, page, listPage.totalPages, 10)

        return PostsViewDto(postsList = listPage.content, viewModel = viewModel)


    }

    private fun setModeltoPagenation(map: HashMap<String, Any>, currentPage: Int, totalPage: Int, pageSize: Int) {
        val currentPagePage: Int = (currentPage - 1) / pageSize + 1
        val totalPagePage: Int = (totalPage - 1) / pageSize + 1

        if (currentPagePage != 1) {
            map["pageFirst"] = "1"
            map["pagePrev"] = ((currentPagePage - 2) * pageSize + 1).toString()
        }
        if (currentPagePage != totalPagePage) {
            map["pageLast"] = totalPage.toString()
            map["pageNext"] = (currentPagePage * pageSize + 1).toString()
        }
        if (currentPage % pageSize != 1) {
            val prevPageNum: MutableList<String> = ArrayList()
            for (i in (currentPagePage - 1) * pageSize + 1 until currentPage) {
                prevPageNum.add(i.toString())
            }
            map["pageCurrentPrevList"] = prevPageNum
        }
        map["pageCurrent"] = currentPage
        if (currentPage % pageSize != 0) {
            val nextPageNum: MutableList<String> = ArrayList()
            for (i in currentPage + 1..Math.min(currentPagePage * pageSize, totalPage)) {
                nextPageNum.add(i.toString())
            }
            map["pageCurrentNextList"] = nextPageNum
        }
    }

}