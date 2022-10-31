


package com.sbs.Chakruk.servicios;


import com.sbs.Chakruk.entidades.Foto;
import com.sbs.Chakruk.entidades.Usuario;
import com.sbs.Chakruk.enums.Rol;
import com.sbs.Chakruk.errores.ErrorServicio;
import com.sbs.Chakruk.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio {
    //permitir que un usuario se registre
    //permitir que un usuario registrado ingrese y edite su cuenta
    //permitir que un usuario deshabilite su cuenta
    //permitir que un usuario pueda volver a habilitar la cuenta
    
   @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    
  
    

    @Transactional
    public void registrar( String nombre,  String mail,String clave1,String clave2 ,MultipartFile archivo, String rol , String sobreMi , String experincia , String educacion , String certificaciones) throws ErrorServicio,Exception{
    
        
  
      
      

      
          validar(nombre, mail,clave1,clave2);
  
    Usuario usuario = new Usuario();
    usuario.setNombre(nombre);
   usuario.setSobreMi(sobreMi);
   usuario.setCertificaciones(certificaciones);
   usuario.setEducacion(educacion);
   usuario.setExperiencia(experincia);
    usuario.setMail(mail);  
usuario.setAlta(new Date());
 usuario.setRol(Rol.USUARIO);
    
  //String encriptada = new BCryptPasswordEncoder().encode(clave1);
 usuario.setClave(clave1);
    
    Foto foto = fotoServicio.guardar(archivo);
    usuario.setFoto(foto);
    
    
    
    
    usuarioRepositorio.save(usuario);
//    
//    notificacionSersouvicio.enviar("Bienvenido a Tinder de Mascotas!", "Tinder de Mascotas", usuario.getMail());
        System.out.println("USUARIO GUARDADO");
    }

    
    
    @Transactional
    public void modificar(String id, String nombre,String sobreMi,String experiencia,String educacion,String certificaciones ) throws ErrorServicio,Exception{
        
        System.out.println("USUARIO ESTA SIENDO MODIFICADO");
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            
            
            
        Usuario usuario=respuesta.get();
       
        usuario.setCertificaciones(certificaciones);
       
        
     
        usuario.setExperiencia(experiencia);
        
        
        usuario.setEducacion(educacion);
        
        
        usuario.setSobreMi(sobreMi);
        
      
        
    usuario.setNombre(nombre);
        
   
    


    
  
    
    
   
        usuarioRepositorio.save(usuario);
        
        }else{
        throw new ErrorServicio ("No se encontro el usuario solicitado.");
        }
    }
    
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio{
    
     Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
        Usuario usuario=respuesta.get();
        
        
        
        
        
 
    usuario.setBaja(new Date());
    
        usuarioRepositorio.save(usuario);
        
        }else{
        throw new ErrorServicio ("No se encontro el usuario solicitado.");
        }
    }
    
    
    
    
       @Transactional
    public void habilitar(String id) throws ErrorServicio{
    
     Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
        Usuario usuario=respuesta.get();
        
        
        
        
        
 
    usuario.setAlta(new Date());
    
        usuarioRepositorio.save(usuario);
        
        }else{
        throw new ErrorServicio ("No se encontro el usuario solicitado.");
        }
    }
    
    

    
    public void validar(String nombre,String mail,String clave,String clave2) throws  ErrorServicio{
        if(nombre==null || nombre.isEmpty()){
        throw new ErrorServicio("El nombre del usuario no puede ser nulo.");
        }
    
         if(mail==null || mail.isEmpty()){
        throw new ErrorServicio("El mail del usuario no puede ser nulo.");
        }
          if(clave==null || clave.isEmpty() || clave.length()<6){
        throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener mas de 6 caracteres.");
        }
          if(!clave.equals(clave2)){
              throw new ErrorServicio("Las claves no coinciden.");
      
    }
    }

  
   @org.springframework.transaction.annotation.Transactional
    public void cambiarRol(String id) throws ErrorServicio{
    
    	Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Usuario usuario = respuesta.get();
    		
    		if(usuario.getRol().equals(Rol.USUARIO)) {
    			
    		usuario.setRol(Rol.ADMIN);
    		
    		}else if(usuario.getRol().equals(Rol.ADMIN)) {
    			usuario.setRol(Rol.USUARIO);
    		}
    	}
    }

    
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
    	
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        
        if (usuario != null) {
        	
            List<GrantedAuthority> permisos = new ArrayList<>();
                        
            //Creo una lista de permisos! 
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol());
            permisos.add(p1);
         
            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", usuario); // llave + valor

            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(usuario.getMail(), usuario.getClave(), permisos);
            
    
            
            return user;

        } else {
            return null;
        }

    }
    
    
    @org.springframework.transaction.annotation.Transactional(readOnly=true)
    public Usuario buscarPorId(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            return usuario;
        } else {

            throw new ErrorServicio("No se encontró el usuario solicitado");
        }

    }
    
    public Boolean logIn(String mail , String clave){
    Boolean vof=false;
    
    Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
    
    if(usuario!=null){
  
    if(clave.equals(usuario.getClave())){
   vof=true;
        System.out.println("USUARIO ENCONTRADO : " + usuario.getClave());
    }
    else{
     System.out.println("CLAVE INCORRECTA: " + clave+ "=! "+ usuario.getClave());
    }

        
        
       
    }
    
    
 
    
    return vof;
    
    }
    
    
    
    
    
   
    
    public Usuario buscarPorMail(String mail){
        
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        
        
        return usuario;
    }
    
    
    public Usuario getOne(String id){
        Usuario usuario=null;
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            
            
             usuario = respuesta.get();
            
        }
        
       return usuario;
    }
    
    
    
    
    
    
    
    

//    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
//    
//        
//        Usuario usuario =usuarioRepositorio.buscarPorMail(mail);
//        
//        if(usuario != null){
//            
//            List<GrantedAuthority> permisos1 = new ArrayList<>();
//            
//            
//            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
//            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
//            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
//            
//            
//            permisos1.add(p1);
//             permisos1.add(p2);
//              permisos1.add(p3);
//              
//               List<String> permisos = new ArrayList<>();
//               permisos.add("MODULO_FOTOS");
//               permisos.add("MODULO_MASCOTAS");
//               permisos.add("MODULO_VOTOS");
//            User user = new User();
//            user.setName(usuario.getMail());
//            user.setPassword(usuario.getClave());
//            user.setRoles(permisos);
//        
//        return (UserDetails) user;
//        }
//        else {
//        
//        return null;
//        }
 
//   @Override
//    public UserDetails  loadUserByUsername(String mail) throws UsernameNotFoundException {
//        
// Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
//        
//        if(usuario != null){
//            
////            List<GrantedAuthority> permisos1 = new ArrayList<>();
////            
////            
////            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
////            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
////            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
////            
//            
//            permisos1.add(p1);
//             permisos1.add(p2);
//              permisos1.add(p3);
//              
//               List<String> permisos = new ArrayList<>();
//               permisos.add("MODULO_FOTOS");
//               permisos.add("MODULO_MASCOTAS");
//               permisos.add("MODULO_VOTOS");
//            User user = new User();
//            user.setName(usuario.getMail());
//            user.setPassword(usuario.getClave());
//            user.setRoles(permisos);
// return     (UserDetails) user;


//    } else{
//        return null;
//       }
        
//    }




  
    
    
   

    
}
