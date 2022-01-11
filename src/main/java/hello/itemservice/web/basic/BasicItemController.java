package hello.itemservice.web.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item(itemName, price, quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }


    /**
     * @ModelAttribute("item") Item item
     * model.addAttribute("item", item); 자동 추가
     */

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item")Item item){

        itemRepository.save(item);

        //model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }



    /**
     * defualt룰 발동함.
     * @ModelAttribute name 생략 가능
     *  model.addAttribute(item); 자동 추가, 생략 가능
     *  생략시 model에 저장되는 name은 "클래스"명 첫글자만 소문자로 등록 Item -> item
     */

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){

        itemRepository.save(item);

        //model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }


    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     */
    @PostMapping("/add")
    public String addItemV4(Item item){

        itemRepository.save(item);

        //model.addAttribute("item", item); //자동 추가, 생략 가능

        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
