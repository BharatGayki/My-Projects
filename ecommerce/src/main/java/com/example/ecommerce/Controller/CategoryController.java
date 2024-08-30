package com.example.ecommerce.Controller;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model , Principal principal){

        if (principal == null){
            return "redirect:/login";
        }

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title" , "Category");
        model.addAttribute("categoryNew" , new Category());
        return "categories";
    }

    // changes as per own customization
    @PostMapping("/add-category")
    public String addCategory(@Valid @ModelAttribute("categoryNew") Category category,
                              BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("failed", "Invalid Category");
            return "redirect:/categories";
        }

        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add due to Duplicate entry");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/categories";
    }

}
