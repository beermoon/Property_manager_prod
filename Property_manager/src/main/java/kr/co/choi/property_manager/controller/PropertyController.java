package kr.co.choi.property_manager.controller;

import kr.co.choi.property_manager.domain.Memo;
import kr.co.choi.property_manager.domain.Property;
import kr.co.choi.property_manager.domain.PropertyType;
import kr.co.choi.property_manager.repository.MemoRepository;
import kr.co.choi.property_manager.repository.PropertyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyRepository propertyRepository;
    private final MemoRepository memoRepository;

    public PropertyController(PropertyRepository propertyRepository,
                                MemoRepository memoRepository) {
        this.propertyRepository = propertyRepository;
        this.memoRepository = memoRepository;
    }

    // 1) 리스트
    @GetMapping
    public String list(Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
        return "properties/list";
    }

    // 2) 등록 폼
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("types", PropertyType.values());
        return "properties/new";
    }

    @PostMapping
    public String create(@RequestParam String title,
                         @RequestParam String address,
                         @RequestParam(required = false) Long price,
                         @RequestParam PropertyType type) {

        Property property = new Property(title, address, price, type);
        propertyRepository.save(property);
        return "redirect:/properties";
    }

    // 4) 상세 (+ 메모 목록)
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Property not found : " + id));

        model.addAttribute("property",property);
        model.addAttribute("memos", memoRepository.findByPropertyIdOrderByCreatedAtDesc(id));
        return "properties/detail";
    }

    // 메모 추가
    @PostMapping("/{id}/memos")
    public String addMemo(@PathVariable Long id,
                          @RequestParam String content) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Property not found : " + id));

        Memo memo = new Memo(content);
        // 연관관계 설정 (Property의 addMemo 사용)
        property.addMemo(memo);      // 연관관계 세팅
        memoRepository.save(memo);   // ✅ 메모를 직접 저장 (확실)

        return "redirect:/properties/" + id;
    }


    // 5) 삭제
    public String delete(@PathVariable Long id) {
        propertyRepository.deleteById(id);
        return "redirect:/properties";
    }


}
