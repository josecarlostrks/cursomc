package com.carlostrks.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.carlostrks.cursomc.domain.Categoria;
import com.carlostrks.cursomc.dto.CategoriaDTO;
import com.carlostrks.cursomc.repositories.CategoriaRepository;
import com.carlostrks.cursomc.services.excepions.DataIntegrityException;
import com.carlostrks.cursomc.services.excepions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	
	public Categoria find(Integer id){
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
//	public Categoria update(Categoria obj) {
//		find(obj.getId());
//		return repo.save(obj);
//	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}	
	
	
	public void delete(Integer id) {
		try {
			repo.delete(find(id));
		}catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma Categoria que possui Produtos.");
		}	
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	private void updateData(Categoria newOjb, Categoria obj) {
		newOjb.setNome(obj.getNome());
	}	
	

}
