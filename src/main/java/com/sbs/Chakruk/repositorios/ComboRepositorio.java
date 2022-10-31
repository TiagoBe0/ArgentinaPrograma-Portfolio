


package com.sbs.Chakruk.repositorios;

import com.sbs.Chakruk.entidades.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepositorio extends JpaRepository<Combo,String> {

}
