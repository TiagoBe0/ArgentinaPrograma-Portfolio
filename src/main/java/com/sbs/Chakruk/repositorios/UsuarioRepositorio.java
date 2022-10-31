


package com.sbs.Chakruk.repositorios;


import com.sbs.Chakruk.entidades.Usuario;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio  extends JpaRepository<Usuario, String>{
    
    
    
//  @Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
//    public Usuario buscarPorMail(@Param("mail")String mail);

      @org.springframework.data.jpa.repository.Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
    public Usuario buscarPorMail(@Param("mail") String mail);
    
    
    
    
}
