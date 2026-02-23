package kr.co.choi.property_manager.controller;

import kr.co.choi.property_manager.controller.dto.PropertyCreateRequest;
import kr.co.choi.property_manager.domain.*;
import kr.co.choi.property_manager.infra.NaverGeocodingClient;
import kr.co.choi.property_manager.repository.MemoRepository;
import kr.co.choi.property_manager.repository.PropertyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kr.co.choi.property_manager.infra.NaverGeocodingClient;

import java.util.HashMap;
import java.util.Map;

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
    public String create(@ModelAttribute PropertyCreateRequest request,
                         Model model) {

       try {
           // 1) 주소 검증 + 지오코딩 (실패하면 throw)
           var point = naverGeocodingClient.geocodeOrThrow(request.getAddress());

           // 2) 엔티티 생성(빈 객체 ) + 값 적용
           Property property = new Property();
           property.updateAll(request);

           // 3) 좌표 저장
           property.updateLocation(point.lat(), point.lng());

           // 4) 저장
           propertyRepository.save(property);

           return "redirect:/properties";

       } catch (Exception e) {

           e.printStackTrace();
           model.addAttribute("error",e.getMessage());

           // 폼 제랜더링용
           model.addAttribute("dealTypes", DealType.values());
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
