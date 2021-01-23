package wfp2.flatlife.requests

data class AddTaskRequest(
    val id: Long = 0,
    val name: String,
    val isComplete: Boolean = false,
    val createdAt: Long,
    val isImportant: Boolean = false
)