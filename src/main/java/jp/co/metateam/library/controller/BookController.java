package jp.co.metateam.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import jp.co.metateam.library.model.BookMstDto;
import jp.co.metateam.library.service.BookMstService;
import lombok.extern.log4j.Log4j2;

/**
 * 書籍関連クラス
 */
@Log4j2
@Controller
public class BookController {
    
    private final BookMstService bookMstService;
    private Object accountService;

    @Autowired
    public BookController(BookMstService bookMstService){
        this.bookMstService = bookMstService;
    }

    @GetMapping("/book/index")
    public String index(Model model) {
        // 書籍を全件取得
        List<BookMstDto> bookMstList = this.bookMstService.findAvailableWithStockCount();
        
        model.addAttribute("bookMstList", bookMstList);

        return "book/index";
    }

    @GetMapping("/book/add")
    public String add(Model model) {
        if (!model.containsAttribute("bookMstDto")) {
            model.addAttribute("bookMstDto", new BookMstDto());
        }

        return "book/add";//登録画面へ
    }
    


//ここから
@PostMapping("/book/add")
        public String addbook(@Valid @ModelAttribute BookMstDto bookMstDto, BindingResult result, RedirectAttributes ra,Model model) {

                 String title = bookMstDto.getTitle();
                 String isbn = bookMstDto.getIsbn();

            boolean errTitleFlg = false;
            boolean errIsbnFlg = false;
            // boolean errIsbnnullFlg = false;
            // boolean errIsbncharacount = false;
            // boolean errIsbncharatype= false;

            List<String> errTitleList = new ArrayList<>();  // エラーメッセージのリスト
            List<String> errIsbnList = new ArrayList<>();

            // 書籍名のバリデーション

            if (StringUtils.isEmpty(title)) {
                errTitleList.add("書籍名は必須です。");
                errTitleFlg = true;       
            }

            // 書籍名が256文字以上の場合、エラーフラグを立てる
            else if (bookMstDto.getTitle().length() >= 255) {
                errTitleList.add("書籍名は255文字以内で入力してください");
                errTitleFlg = true;
            }

            // ISBNのバリデーション

            if (StringUtils.isEmpty(isbn)) {
                errIsbnList.add("ISBNは必須です");
                result.rejectValue("isbn", "error.required", "ISBNは必須です");
                errIsbnFlg = true;
            }

            // ISBNが13文字以外の場合、エラーフラグを立てる

            else {
            if (isbn.length() != 13) {
                errIsbnList.add("ISBNは13桁で入力してください");
                result.rejectValue("isbn", "error.length", "ISBNは13桁で入力してください");
                errIsbnFlg = true;

            }

            // ISBNが数字のみで構成されていることを確認する正規表現

            if (!isbn.matches("[0-9]+")) {
                errIsbnList.add("ISBNは半角数字で入力してください");
                result.rejectValue("isbn", "error.format", "ISBNは半角数字で入力してください");
                errIsbnFlg = true;

            } 

            if(!errIsbnFlg){
                var existingBook = bookMstService.selectByIsbn(isbn);
                if(existingBook != null){
                    errIsbnList.add("このISBNは登録済みです");
                    result.rejectValue("isbn","error.duplicate","このISBNは登録済みです");
                    errIsbnFlg = true;
                }
            }
        }

        if(errTitleFlg || errIsbnFlg){
            model.addAttribute("errTitleList", errTitleList);
            model.addAttribute("errIsbnList", errIsbnList);
            model.addAttribute("bookMstDto", bookMstDto);
            return "book/add";
            
        }









        bookMstService.save(bookMstDto);
        

            return "redirect:/book/index";
        }
    }
    
//ここに新しく追加する　PostMappingPostMapping　　保存されたらIndex.htmlへ移動したい
// return "redirect:login";

