package WithYou.domain.scrap.controller;

import WithYou.domain.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    
}
