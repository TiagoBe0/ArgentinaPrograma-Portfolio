


package com.sbs.Chakruk.controladores;

import com.sbs.Chakruk.entidades.Usuario;
import com.sbs.Chakruk.errores.ErrorServicio;
import com.sbs.Chakruk.servicios.FotoServicio;
import com.sbs.Chakruk.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
      @Autowired
    private PortalControlador portalControlador;
    
//    @Autowired
//    private FotoServicio fotoServicio;
    

    
    
       
    @GetMapping("/usuarioRegistro")
    public String registroUsuario() throws ErrorServicio{
    
   
       
    return "registroWeb.html";
       
    }
    
    
    
    
        @GetMapping("/login")
    public String logIn() throws ErrorServicio{
    
   
        
       
    return "loginWeb.html";
       
    }
        
            //   REGISTRAR
     
      @PostMapping("/registroUsuario")
    public String registrar(@RequestParam String nombre,@RequestParam String mail,@RequestParam String clave1, @RequestParam String clave2 ,MultipartFile archivo, String rol,String sobreMi, String experiencia , String educacion,String certificaciones) throws ErrorServicio, Exception{
      
         
          System.out.println(nombre);
          System.out.println(clave1);
       try{
         if(archivo!=null){
    usuarioServicio.registrar( nombre, mail, clave1, clave2, archivo, rol,sobreMi,experiencia,educacion,certificaciones );
         }
      return "index.html";
       }catch(ErrorServicio ex){
           
           
           return "registroWeb.html";
       }
    }
    
       @PostMapping("/modificarUsuario/{idUsuario}")
        public String modificarUsuario(@PathVariable String idUsuario, ModelMap modelo, String educacion , String experiencia, String certificaciones , String sobreMi ,MultipartFile archivo ,String nombre ) throws Exception{
        
        Usuario usuario = usuarioServicio.buscarPorId(idUsuario);
        modelo.put("id",usuario.getId());
        modelo.put("sobreMi",usuario.getSobreMi());
        modelo.put("experiencia", usuario.getExperiencia());
        modelo.put("educacion",usuario.getEducacion());
        modelo.put("certificaciones", usuario.getCertificaciones());
            usuarioServicio.modificar(archivo, idUsuario, nombre, sobreMi, experiencia, educacion, certificaciones);
            
          
        
        return "index.html";
        }
    
    
    
    
    
    
}
