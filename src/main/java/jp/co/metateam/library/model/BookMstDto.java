package jp.co.metateam.library.model;

import java.math.BigInteger;
import java.security.Timestamp;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 書籍マスタDTO
 */
@Getter
@Setter
public class BookMstDto {
    
    private Long id; 
    
    @NotEmpty(message = "ISBNは必須です")
    @Size(max = 13,message = "ISBNは13文字以下で入力してください")
    private String isbn;

    @NotEmpty(message = "書籍名は必須です")
    @Size(max = 255,message = "書籍名は255文字以下で入力してください")
    private String title;
    

    private Timestamp deletedAt;

    private BookMst bookMst;
}


// public class AccountDto {

//     @NotEmpty(message = "社員番号は必須です")
//     @Size(max = 50)
//     private String employeeId;

//     @NotEmpty(message = "氏名は必須です")
//     @Size(max = 255)
//     private String name;

//     @NotEmpty(message = "メールアドレスは必須です")
//     @Email(message = "メールアドレスの形式が不正です")
//     private String email;

//     @NotEmpty(message = "パスワードは必須です")
//     @Size(min = 5, message="パスワードは5文字以上で入力してください")
//     private String password;

//     private Integer authorizationType = 1;
// }
