package br.univille.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.univille.entity.Cliente;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteControllerAPI {
    
    private final ClienteService service = new ClienteService();

    @GetMapping
    public ResponseEntity<List<Cliente>> getClientes(){
        return new ResponseEntity<List<Cliente>>
                    (HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cliente> postCliente(@RequestBody Cliente cliente){
        if (cliente.getId() == 0){
            service.save(cliente);
            return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> putCliente(@PathVariable long id,
                                                @RequestBody Cliente cliente){
        var clienteAntigo = service.getById(id);
        if(clienteAntigo == null){
            return ResponseEntity.notFound().build();
        }

        ((Cliente) clienteAntigo).setNome(cliente.getNome());
        ((Cliente) clienteAntigo).setEndereco(cliente.getEndereco());
        ((Cliente) clienteAntigo).setDataNascimento(cliente.getDataNascimento());

        service.save(clienteAntigo);

        return new ResponseEntity<Cliente>(HttpStatus.OK);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable long id){
        var clienteExcluido = service.getById(id);
        if(clienteExcluido == null){
            return ResponseEntity.notFound().build();
        }

        service.delete(id);

        return new ResponseEntity<Cliente>(HttpStatus.OK);
    }



}
