package com.dkds.limited_api.controller;

import com.dkds.limited_api.dto.Area;
import com.dkds.limited_api.dto.RectangleDimensions;
import com.dkds.limited_api.dto.TriangleDimensions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/area")
public class AreaCalculationController {

    @PostMapping(value = "/rectangle")
    public ResponseEntity<Area> rectangle(@RequestBody RectangleDimensions dimensions) {
        return ResponseEntity.ok(new Area(dimensions.length() * dimensions.width()));
    }

    @PostMapping(value = "/triangle")
    public ResponseEntity<Area> triangle(@RequestBody TriangleDimensions dimensions) {
        return ResponseEntity.ok(new Area(0.5d * dimensions.height() * dimensions.base()));
    }
}