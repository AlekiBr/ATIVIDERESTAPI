package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Pecas;
import application.repository.PecasRepository;

@RestController
@RequestMapping("/pecas")
public class PecasController {
    @Autowired
    private PecasRepository pecaRepo;

    @GetMapping
    public Iterable<Pecas> getAll() {
        return pecaRepo.findAll();
    }

    @PostMapping
    public Pecas post(@RequestBody Pecas peca) {
        return pecaRepo.save(peca);
    }

    @GetMapping("/{id}")
    public Pecas getOne(@PathVariable long id) {
        Optional<Pecas> resultado = pecaRepo.findById(id);
        if(resultado.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Peça não encontrada"
            );
        }
        return resultado.get();
    }

    @PutMapping("/{id}")
    public Pecas put(@PathVariable long id, @RequestBody Pecas novosDados){
        Optional<Pecas> resultado = pecaRepo.findById(id);

        if(resultado.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Peça não encontrada"
            );
        }

        resultado.get().setDescricao(novosDados.getDescricao());
        resultado.get().setEstado(novosDados.isEstado());

        return pecaRepo.save(resultado.get());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if(!pecaRepo.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Peça não encontrada"
            );
        }

        pecaRepo.deleteById(id);
    }
}