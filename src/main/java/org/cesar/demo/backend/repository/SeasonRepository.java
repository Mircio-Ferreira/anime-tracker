package org.cesar.demo.backend.repository;

import org.cesar.demo.backend.entity.Season;
import org.cesar.demo.backend.enums.Seasonal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.time.Year;

public interface SeasonRepository extends JpaRepository <Season,Long> {

    Optional<Season> findByYearAndSeasonal(Year year, Seasonal seasonal);


}
