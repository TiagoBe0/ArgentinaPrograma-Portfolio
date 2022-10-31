


package com.sbs.Chakruk.servicios;

import com.sbs.Chakruk.entidades.Foto;
import com.sbs.Chakruk.entidades.Galeria;
import com.sbs.Chakruk.errores.ErrorServicio;
import com.sbs.Chakruk.repositorios.GaleriaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



   @Service
public class GaleriaServicio {
    
    @Autowired
    private GaleriaRepositorio galeriaRepositorio;

     @Autowired
    private FotoServicio fotoServicio;
  
    @Transactional
   public void guardar(Foto foto, String idGaleria) throws ErrorServicio{
       
   
   Optional<Galeria> respuesta = galeriaRepositorio.findById(idGaleria);
   
   
    if(respuesta.isPresent()){
   Galeria galeria = respuesta.get();
   
   
   List<Foto> fotos = galeria.getFotos();
   
   fotos.add(foto);
   
    }else{
        throw new ErrorServicio ("No se encontro la galeria seleccionada.");
        }
   
    
   }
 
    
       
   @Transactional
 public Galeria crearGaleria(String nombre , Integer anio  ){
 
 
 Galeria galeria =  new Galeria();
 
 galeria.setAnio(anio);
 galeria.setNombre(nombre);
 
 galeriaRepositorio.save(galeria);
 
     
     return galeria;
 }
    
    
 @Transactional
 public List<Foto> agregarFoto(Foto foto , Galeria galeria) throws ErrorServicio{
 
     
     
  
   List<Foto> fotos = galeria.getFotos();
   fotos.add(foto);
   galeria.setFotos(fotos);
 
  return  fotos;
 }
 
 public List<Galeria> verTodo(){
 
     
    List<Galeria> galerias = galeriaRepositorio.findAll();
 
 return galerias;
 }
    
 
 
 
 
 
 public Galeria getOne(String idGaleria){
 
 Galeria galeria = galeriaRepositorio.getById(idGaleria);
     
     return galeria;
 }
 
 
 
 
 
 
 @Transactional
     public void  borrar(String id){
        
         
         
       
        
        galeriaRepositorio.deleteById(id);
        
        
        
        
        
    }
}
