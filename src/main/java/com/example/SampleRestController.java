package com.example;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/endpoints")
public class SampleRestController {

   List<Sample> samples = new ArrayList<>();

   public List<Sample> getSamples() {
      return samples;
   }

   @PostMapping
   public Sample addSample(@RequestParam String name, @RequestParam String description) {
      Sample newSample = Sample.builder().name(name).description(description).build();
      samples.add(newSample);
      return newSample;
   }

   @PostMapping("/clear")
   public int clearSamples() {
      int size = samples.size();
      samples.clear();
      return size;
   }

   @Data
   @Builder
   public static class Sample {
      private String name;
      private String description;
   }

}
