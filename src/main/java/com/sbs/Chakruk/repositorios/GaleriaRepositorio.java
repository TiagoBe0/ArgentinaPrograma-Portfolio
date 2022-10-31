


package com.sbs.Chakruk.repositorios;

import com.sbs.Chakruk.entidades.Galeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GaleriaRepositorio extends JpaRepository<Galeria,String> {

}
