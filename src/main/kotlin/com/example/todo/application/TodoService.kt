package com.example.todo.application

import com.example.todo.domain.model.Todo
import com.example.todo.domain.service.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

/**
 * todoサービス
 */
@Service
class TodoService(
  private val todoRepository: TodoRepository
) {
  /** 全件取得 */
  fun getAllTodos(): List<Todo> = todoRepository.findByOrderByCreatedAtDesc()

  /** todoをIDで検索 */
  fun getTodoById(id: Long): Todo = todoRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("No TODO which has such id")

  /** todo登録 */
  fun registerTodo(todo: Todo): Todo = todoRepository.save(todo)

  /** 既存タイトルの重複を検出 */
  fun isTitleDuplicated(title: String): Boolean = todoRepository.findByTitle(title) != null

  /** 異なるIDの既存タイトルとの重複を検出 */
  fun isTitleDuplicated(id: Long, title: String): Boolean = todoRepository.findByIdNotAndTitle(id, title) != null

  /** todo削除 */
  fun deleteTodo(id: Long) = todoRepository.deleteById(id)

  /** todo更新 */
  fun updateTodo(id: Long, title: String): Todo {
    val todo = getTodoById(id)
    todo.title = title
    return todoRepository.save(todo)
  }
}