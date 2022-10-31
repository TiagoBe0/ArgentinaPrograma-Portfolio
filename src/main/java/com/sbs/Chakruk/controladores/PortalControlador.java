


package com.sbs.Chakruk.controladores;


import com.sbs.Chakruk.entidades.Foto;
import com.sbs.Chakruk.entidades.Galeria;
import com.sbs.Chakruk.entidades.Usuario;

import com.sbs.Chakruk.errores.ErrorServicio;

import com.sbs.Chakruk.servicios.FotoServicio;
import com.sbs.Chakruk.servicios.GaleriaServicio;
import com.sbs.Chakruk.servicios.UsuarioServicio;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
  
    @Autowired
    private GaleriaServicio galeriaServicio;
    
    @Autowired
    private FotoServicio fotoServicio;
       @Autowired
    private UsuarioServicio usuarioServicio;
   
          //   INDEX
      
    @GetMapping("/")
    public String index(ModelMap modelo) throws ErrorServicio{
       Usuario usuario = usuarioServicio.buscarPorId("e5d38f54-fdee-406c-ae09-c0551480c643");
        if(usuario!= null){
             modelo.put("idUsuario",usuario.getId());
        modelo.put("sobreMi",usuario.getSobreMi());
        modelo.put("experiencia", usuario.getExperiencia());
        modelo.put("educacion",usuario.getEducacion());
       modelo.put("certificaciones", usuario.getCertificaciones());
        }
    
    return "index.html";
       
    }
    
    @GetMapping("/usuarioRegistro")
    public String registroUsuario() throws ErrorServicio{
    
   
       
    return "registroWeb.html";
       
    }
    
    
    
    
        @GetMapping("/login")
    public String logIn() throws ErrorServicio{
    
   
        
       
    return "loginWeb.html";
       
    }
    
    
    
      //   FOTOS
    
   
      @GetMapping("/fotografia")
    public ResponseEntity<byte[]> foto(@RequestParam String idFoto){
    
        
        try {
            Foto fotoG = fotoServicio.getOne(idFoto);
            
            if(fotoG ==null){
                
                System.out.println("La galeria esta vacia.");
            }
        byte[] foto = fotoG.getContenido();
        
     if(foto == null){
                
                System.out.println("La foto no existe");
            }
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
            
        return new ResponseEntity<> (foto,headers,HttpStatus.OK);
            
       } catch (Exception ex ) {
           
           Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE ,null , ex);
           return new ResponseEntity<> (HttpStatus.NOT_FOUND);
       }
    
    
    }
    
    
    

    
     //   VER-GALERIAS
       @GetMapping("/galerias")
    public String verGalerias(ModelMap modelo){
        
        
        List<Galeria> galerias = galeriaServicio.verTodo();
        
        
        modelo.put("galerias", galerias);
        
    
    return "galeria.html";
    } 
    
     //   VER-ALBUM
    
           @GetMapping("/album/{idAlbum}")
    public String verAlbum(ModelMap modelo , @PathVariable String idAlbum){
        
        Galeria galeria = galeriaServicio.getOne(idAlbum);
        
        
        
        modelo.put("galeria", galeria);
        modelo.put("fotos", galeria.getFotos());
        
    
    return "albumcss.html";
    } 
    
    
   @GetMapping("/foto/{idGaleria}")
    public ResponseEntity<byte[]> fotoAlbum(@PathVariable String idGaleria , ModelMap modelo){
    
        Galeria galeria = galeriaServicio.getOne(idGaleria);
        byte[] foto = galeria.getFotos().get(0).getContenido();
    
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        
        return new ResponseEntity<> (foto,headers,HttpStatus.OK);
//               return new ResponseEntity<> (fHttpStatus.NOT-FOUND);
    }    
    //   RESPONSE-ENTITY
    
 
    
    
    
    
    
    
    
    //    CONFIGURACION GALERIA
       @GetMapping("/configuraciongaleria")
    public String configuracionGaleria(ModelMap modelo){
        
      List<Galeria> galerias = galeriaServicio.verTodo();
        
    modelo.put("galerias",galerias);
    return "configuraciongaleria.html";
    } 
    
        //    NUEVA GALERIA
       @PostMapping("/nuevagaleria")
    public String nuevaGaleria(String nombre , Integer anio){
        
     Galeria galeria = galeriaServicio.crearGaleria(nombre, anio);
       
        
    return "configuraciongaleria.html";
    } 
    
    
    
      //   MODIFICAR GALERIA
     @GetMapping("/accionesConGaleria/{idGaleria}")
        public String modificarGaleria(@PathVariable String idGaleria , ModelMap modelo){
        
            Galeria galeria = galeriaServicio.getOne(idGaleria);
            
           List<Foto> fotos = galeria.getFotos();
           
           
           modelo.put("fotos", galeria.getFotos());
           modelo.put("galeria",galeria);
           
        
        return "agregarfotogaleria.html";
        }
    
      //  ELIMINAR FOTO
     @PostMapping("/eliminarFotoGaleria/{idFoto}")
        public String eliminarFotoGaleria(@PathVariable String idFoto , ModelMap modelo){
        
            fotoServicio.borrar(idFoto);
            
         
           
        
        return "agregarfotogaleria.html";
        }
        
        
        
         //  ELIMINAR GALERIA
     @PostMapping("/eliminarGaleria/{idGaleria}")
        public String eliminarGaleria(@PathVariable String idGaleria , ModelMap modelo){
        
            galeriaServicio.borrar(idGaleria);
            
         
           
        
        return "configuraciongaleria.html";
        }
        
         //   AGREGAR GALERIA
        
        @PostMapping("/agregarfotogaleria/{idGaleria}")
        public String agregarFotoGaleria(@PathVariable String idGaleria , ModelMap modelo ,  MultipartFile archivo) throws ErrorServicio{
            
            if(archivo!=null){
           Foto foto = fotoServicio.guardar(archivo);
        
            Galeria galeria = galeriaServicio.getOne(idGaleria);
            
          List<Foto> fotos =galeriaServicio.agregarFoto(foto, galeria);
           
           
           
           modelo.put("fotos", fotos);
           modelo.put("galeria",galeria);
            }
            else{throw new ErrorServicio("Debe seleccionar un archivo.");}
        
        return "agregarfotogaleria.html";
        }
   
   
       
    
      @GetMapping("/inicioSecion")
    public String inicioSecion(@RequestParam String mail, @RequestParam String clave, ModelMap modelo,String idUsuario,String educacion , String experiencia, String certificaciones , String sobreMi ,MultipartFile archivo ,String nombre) throws ErrorServicio, Exception{
    
   if(usuarioServicio.logIn(mail, clave)){
       
           Usuario usuario = usuarioServicio.buscarPorMail(mail);
           if(usuario !=null){
         modelo.put("idUsuario",usuario.getId());
        modelo.put("sobreMi",usuario.getSobreMi());
        modelo.put("experiencia", usuario.getExperiencia());
        modelo.put("educacion",usuario.getEducacion());
       modelo.put("certificaciones", usuario.getCertificaciones());
           
   }
 modelo.put("login", clave);
   return "indexEdit.html";
   }
    return "loginWeb.html";
    }
    
  
    @PostMapping("/usuarioModificar/{idUsuario}")
   public String modificarUsuario(  ModelMap modelo,@RequestParam String idUsuario,String educacion , String experiencia, String certificaciones , String sobreMi  ,String nombre) throws Exception{
       
         Usuario usuario = usuarioServicio.getOne(idUsuario);
         
        System.out.println("USUARIO MODIFICAR");
           if(usuario !=null){
               
         modelo.put("idUsuario",usuario.getId());
        modelo.put("sobreMi",usuario.getSobreMi());
        modelo.put("experiencia", usuario.getExperiencia());
        modelo.put("educacion",usuario.getEducacion());
       modelo.put("certificaciones", usuario.getCertificaciones());
       
       
            usuarioServicio.modificar( idUsuario, nombre, sobreMi, experiencia, educacion, certificaciones);
    
       
       return "indexEdit.html";
   }
           
           return "indexEdit.html";
   }
   
   
}
