package com.example.ecommerce.Controller;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public String categories(Model model , Principal principal,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "3") int size){


        if (principal == null){
            return "redirect:/login";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryService.findPaginated(pageable); // Fetch paginated data

        // Add pagination data to the model
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("currentPage", page);


        //adding paginated categories to model
        model.addAttribute("categories", categoryPage.getContent());

       // fecteches the all category thats why replaced it by the above line which takes the paginated data
        //model.addAttribute("categories", categoryService.findAll());// finding all categories

        return "categories";
    }


// adding new categories
    @GetMapping("/showNewCategoryForm")
    public String showNewCategories( Model model){

        Category category = new Category();
        model.addAttribute("category", category);
        return "add_new_categories";


    }

// saving the categories
   /* @PostMapping("/saveCategory")
        public String saveCategory(@Valid @ModelAttribute("category") Category category,
                                   BindingResult result, Model model){

        if (result.hasErrors()) {
            return "add_new_categories";
        }

        categoryService.save(category);
        return "redirect:/categories";
        }*/
@PostMapping("/saveCategory")
public String saveCategory(@Valid @ModelAttribute("category") Category category,
                           BindingResult result, Model model) {
    if (result.hasErrors()) {
        return "add_new_categories";  // Return to the form page if validation fails
    }
    try {
        categoryService.save(category);
    } catch (DataIntegrityViolationException e) {
        model.addAttribute("error", "Duplicate entry for category name");
        return "add_new_categories";
    }
    categoryService.save(category);
    return "redirect:/categories";
}

    @PostMapping("/updateCategory")
    public String updateCategory(@Valid @ModelAttribute("category_update") Category category,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update_new_categories";  // return to the update form page if validation fails
        }
        try {
            categoryService.update(category);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Duplicate entry for category name");
            return "update_new_categories";
        }
        categoryService.save(category);
        return "redirect:/categories";
    }

// update the categories

    @GetMapping("/showFormForUpdate/{category_id}")
    public String showFormForUpdate( @PathVariable(value = "category_id") Long categoryId, Model model){

        Category category = categoryService.findById(categoryId);

        model.addAttribute("category_update", category);
        return "update_new_categories";
    }

// Delete Category
    @GetMapping("/deleteCategory/{category_id}")
    public String deleteCategories(@PathVariable (value = "category_id")Long categoryId){

        this.categoryService.deleteById(categoryId);
        return "redirect:/categories";
    }


//    // DELETE CATEGORIES
//    @RequestMapping(value = "/delete-category" , method = {RequestMethod.PUT,RequestMethod.GET})
//    (public String delete(Long id, RedirectAttributes attributes) {
//        try {
//
//            categoryService.deleteById(id);
//            System.out.println(id);
//            attributes.addFlashAttribute("success", "Deleted successfully");
//        }catch (Exception e){
//
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Delete successfully danger ");
//        }
//        return "redirect:/categories";
//    }












    /*Previous customization*/
/*

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


  */
/*  @RequestMapping(value = "/findById" , method = {RequestMethod.PUT  , RequestMethod.GET})
    @ResponseBody
     public Category findById(Long id) {
        return categoryService.findById(id);
    }*//*


    @RequestMapping(value = "/findById", method = {RequestMethod.PUT,RequestMethod.GET})
    @ResponseBody
    public Category findById(@RequestParam Long id) {
        return categoryService.findById(id);
    }


*/
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
*//*


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


*/
/*    @GetMapping("/showUpdates/{id}")
    public String showUpdate(@PathVariable(value = "id")long id, Model model) {
        Category category;

        category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "redirect:/categories";
    }*//*




*/


}
