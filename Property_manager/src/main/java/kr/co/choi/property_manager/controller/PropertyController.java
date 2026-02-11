package kr.co.choi.property_manager.controller;

import kr.co.choi.property_manager.domain.Memo;
import kr.co.choi.property_manager.domain.Property;
import kr.co.choi.property_manager.domain.PropertyStatus;
import kr.co.choi.property_manager.domain.PropertyType;
import kr.co.choi.property_manager.infra.NaverGeocodingClient;
import kr.co.choi.property_manager.repository.MemoRepository;
import kr.co.choi.property_manager.repository.PropertyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kr.co.choi.property_manager.infra.NaverGeocodingClient;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyRepository propertyRepository;
    private final MemoRepository memoRepository;
    private final NaverGeocodingClient naverGeocodingClient;

    public PropertyController(PropertyRepository propertyRepository,
                                MemoRepository memoRepository,
                            NaverGeocodingClient naverGeocodingClient) {
        this.propertyRepository = propertyRepository;
        this.memoRepository = memoRepository;
        this.naverGeocodingClient = naverGeocodingClient;
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
                         @RequestParam PropertyType type,
                         Model model) {

       try {
           var point = naverGeocodingClient.geocodeOrThrow(address);

           Property property = new Property(title, address, price, type, PropertyStatus.ACTIVE);
           property.setLat(point.lat());
           property.setLng(point.lng());

           propertyRepository.save(property);
           return "redirect:/properties";

       } catch (IllegalArgumentException e) {
           // A 전략 : 실패하면 등록 화면으로 되돌리고 메세지 표시
           model.addAttribute("error",e.getMessage());
           model.addAttribute("types",PropertyType.values());
           return "properties/new";
       }

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
