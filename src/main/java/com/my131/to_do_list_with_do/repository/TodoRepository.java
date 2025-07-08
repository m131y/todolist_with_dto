package com.my131.to_do_list_with_do.repository;

import com.my131.to_do_list_with_do.model.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Todo> todoRowMapper = (resultSet,rowNum) -> {
        Todo todo = Todo.builder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("title"))
                .completed(resultSet.getBoolean("completed"))
                .userId(resultSet.getInt("user_id"))
                .build();

        return todo;
    };

    public List<Todo> findAllByUserId(int userId) {
        String sql = "SELECT * FROM todo WHERE user_id = ? ORDER BY id";

        return jdbcTemplate.query(sql, todoRowMapper, userId);
    }

    public Todo findByIdAndUserId(int id, int userId) {
        String sql = "SELECT * FROM todo WHERE id = ? And user_id = ?";

        return jdbcTemplate.queryForObject(sql, todoRowMapper, id, userId);
    }

    public int save(Todo todo) {
        String sql = "INSERT INTO todo (user_id, title, completed) VALUES (?,?,?)";

        return jdbcTemplate.update(sql, todo.getUserId(), todo.getTitle(), todo.isCompleted());
    }

    public int update(Todo todo) {
        String sql = "UPDATE todo SET title = ?, completed = ? WHERE id = ? AND user_id = ?";

        System.out.println("Repo update");
        System.out.println("todo id: " + todo.getId() + "todo title: " + todo.getTitle() + "todo completed: "  + todo.isCompleted() + "todo userid: " + todo.getUserId());
        return jdbcTemplate.update(sql, todo.getTitle(), todo.isCompleted(), todo.getId(), todo.getUserId());
    }

    public int deleteByIdAndUserId(int id, int userId) {
        String sql = "DELETE FROM todo WHERE id = ? AND user_id = ?";

        return jdbcTemplate.update(sql, id, userId);
    }

}
