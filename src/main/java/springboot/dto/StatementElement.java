package springboot.dto;

import lombok.Data;

// класс представляющий собой элемент ведомости
@Data
public class StatementElement {
    String username;
    String taskName;
    // зачтено или не зачтено
    String status;
}
