package wiki.feh.api.fehvgalertapikotlin.domain.vginfo.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.domain.VgInfo

@Repository
interface VgInfoRepository : JpaRepository<VgInfo, Long>{
}