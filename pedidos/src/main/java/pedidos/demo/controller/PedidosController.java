package pedidos.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedidos.demo.dto.PaymentDTO;
import pedidos.demo.dto.PedidosDTO;
import pedidos.demo.dto.PedidosUserDTO;
import pedidos.demo.service.PedidosService;
import pedidos.demo.service.TokenService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("pedidos")
public class PedidosController {


    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<PedidosDTO>> buscarPedidos(){
        List<PedidosDTO> resultado = pedidosService.buscarPedidos();

        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/cpf")
    public ResponseEntity<Optional<List<PedidosDTO>>> buscarPedidoPorCpf(@RequestBody Map<String, String> body){

       Optional<List<PedidosDTO>> dtoList = pedidosService.buscaPorCPF(body.get("cpf"));

        if(dtoList.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(dtoList);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<PedidosUserDTO> cadastrar(@RequestBody PedidosDTO dto, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.replace("Bearer ", ""); // Remove o prefixo "Bearer "

        // Decodifica o token e extrai o UUID do usuário
        String usuarioId = tokenService.getUuidFromToken(token);
        String usuarioEmail = tokenService.getEmailFromToken(token);
        PedidosUserDTO pedido = pedidosService.cadastrar(dto,usuarioEmail,usuarioId);


        PaymentDTO message = new PaymentDTO(pedido);
        rabbitTemplate.convertAndSend("pedido.cadastro", message);


        return ResponseEntity.ok(pedido);
    }

   /* @GetMapping("/teste")
    public ResponseEntity<String> teste(HttpServletRequest request) {
        // Obtém o token JWT da requisição
        String authorizationHeader = request.getHeader("Authorization");

        // Verifica se o cabeçalho de autorização existe e se começa com "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", ""); // Remove o prefixo "Bearer "

            // Decodifica o token e extrai o UUID do usuário
            String usuarioId = tokenService.getUuidFromToken(token);

            // Retorna o UUID do usuário extraído do token
            return ResponseEntity.ok(usuarioId);
        }

        // Retorna erro se o cabeçalho Authorization não for válido ou ausente
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token não fornecido ou inválido");
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<PedidosDTO> buscaPedidoPorId(@PathVariable long id){

        Optional<PedidosDTO> pedido = pedidosService.buscaPorId(id);
        if(pedido.isEmpty()){
            throw new RuntimeException("Esse ID não foi encontrado no banco de dados");
        }else {
            return pedido.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
    }

    @Transactional
    @DeleteMapping("/apagar/{id}")
    public ResponseEntity apagarPedidoPorId(@PathVariable Long id){

       Optional<PedidosDTO> pedido = pedidosService.buscaPorId(id);

       if(pedido.isEmpty()){
           throw new RuntimeException("Esse ID não foi encontrado no banco de dados");
       }else {
           pedidosService.deletarPorId(id);
           return ResponseEntity.noContent().build();
       }
    }

/*
    @PatchMapping("atualizar/{id}")
    public ResponseEntity atualizarPedido(@PathVariable Long id){

    }
    */
}
