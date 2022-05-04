package wiki.feh.api.fehvgalertapikotlin.domain.posts.dto

class PostsViewDto (
    val postsList: List<PostsListResponseDto>,
    val viewModel : Map<String, Any>
    ) {
}