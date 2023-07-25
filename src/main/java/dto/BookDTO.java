package dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private int price;
    private String coverFileName;
}
