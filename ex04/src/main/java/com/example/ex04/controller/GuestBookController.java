package com.example.ex04.controller;

import com.example.ex04.dto.GuestBookDTO;
import com.example.ex04.dto.PageRequestDTO;
import com.example.ex04.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Slf4j
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list...... " + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto... " + dto);

        Long id = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", id);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("id : {}", id);
        GuestBookDTO dto = service.read(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(Long id, RedirectAttributes redirectAttributes) {

        log.info("id: {}", id);
        service.remove(id);
        redirectAttributes.addFlashAttribute("msg", id);
        return "redirect:/guestbook/list";

    }

    @PostMapping("/modify")
    public String modify(GuestBookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify...............................");
        log.info("dto : " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("id", dto.getId());

        return "redirect:/guestbook/read";
    }

}
