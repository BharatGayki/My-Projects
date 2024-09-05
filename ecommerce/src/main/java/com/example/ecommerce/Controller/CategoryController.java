package com.example.ecommerce.Controller;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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


  /*  @RequestMapping(value = "/findById" , method = {RequestMethod.PUT  , RequestMethod.GET})
    @ResponseBody
     public Category findById(Long id) {
        return categoryService.findById(id);
    }*/

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT,RequestMethod.GET})
    @ResponseBody
    public Category findById(@RequestParam Long id) {
        return categoryService.findById(id);//category_id
    }


/*
    orignal
    @PostMapping("/update-category")
    public String update(@Valid @ModelAttribute Category category , RedirectAttributes attributes ) {

        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success", "Updated successfully");
         } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add due to Duplicate entry");
        } catch (Exception e){
        e.printStackTrace();
        attributes.addFlashAttribute("failed", "Failed to update due to Duplicate entry");
    }
        return "redirect:/categories";
    }
*/

//searched
    @PostMapping("/update-category")
    public String update(@Valid @ModelAttribute("category") Category category, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
             attributes.addFlashAttribute("failed", "Invalid Category Data");
            return "redirect:/categories";

        }

        try {
            categoryService.update(category);
            attributes.addFlashAttribute("success", "Category updated successfully");
        } catch (DataIntegrityViolationException e) {
            attributes.addFlashAttribute("failed", "Duplicate entry found");
        } catch (Exception e) {
            attributes.addFlashAttribute("failed", "An error occurred while updating the category");
        }

        return "redirect:/categories";
    }


/*    @GetMapping("/showUpdates/{id}")
    public String showUpdate(@PathVariable(value = "id")long id, Model model) {
        Category category;

        category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "redirect:/categories";
    }*/


    @RequestMapping(value = "/delete-category" , method = {RequestMethod.PUT,RequestMethod.GET})
    public String delete(Long id, RedirectAttributes attributes) {
        try {

            categoryService.deleteById(id);
            System.out.println(id);
            attributes.addFlashAttribute("success", "Deleted successfully");
        }catch (Exception e){

            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Delete successfully danger ");
        }
        return "redirect:/categories";
    }



}
