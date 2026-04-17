package com.sprint.BookInventoryMgmt.BookMgmt.Controller;

// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody;
// import lombok.RequiredArgsConstructor;
// import java.util.List;
// import com.sprint.BookInventoryMgmt.BookMgmt.Repository.PublisherRepository;
// import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Publisher;

// @RestController
// @RequestMapping("/api/v1/publishers")
// @RequiredArgsConstructor
public class PublisherController {
}


//     private final PublisherRepository publisherRepository;

//     @GetMapping
//     public List<Publisher> getAll() {
//         return publisherRepository.findAll();
//     }

//     @GetMapping("/{id}")
//     public Publisher getById(@PathVariable Integer id) {
//         return publisherRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Publisher not found"));
//     }

//     @PostMapping
//     public Publisher create(@RequestBody Publisher publisher) {
//         return publisherRepository.save(publisher);
//     }

//     @PutMapping("/{id}")
//     public Publisher update(@PathVariable Integer id,
//                             @RequestBody Publisher publisher) {

//         publisher.setPublisherId(id);
//         return publisherRepository.save(publisher);
//     }

//     @DeleteMapping("/{id}")
//     public void delete(@PathVariable Integer id) {
//         publisherRepository.deleteById(id);
//     }
// }