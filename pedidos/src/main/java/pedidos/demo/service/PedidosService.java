package pedidos.demo.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pedidos.demo.dto.*;
import pedidos.demo.model.*;
import pedidos.demo.repository.PedidoCreditoRepository;
import pedidos.demo.repository.PedidosRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidosService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PedidosRepository pedidosRepository;
    @Autowired
    private PedidoCreditoRepository creditoRepository;


    @Autowired
    private ModelMapper modelMapper;

    public Optional<List<PedidosDTO>> buscaPorCPF(String cpf) {
        List<Pedido> resultado = pedidosRepository.findByCpf(cpf);

        return Optional.of(resultado.stream()
                .map(pedido -> new PedidosDTO(pedido))
                .collect(Collectors.toList()));
    }

    public PedidosUserDTO cadastrar(PedidosDTO dto,String email, String uuid) {
        Pedido pedido;
        PedidoCredito credito = null;


        switch (dto.getFormaDePagamento()){
            case CREDITO:
                pedido = new PedidoCredito(
                        dto.getNumeroCartao(),
                        dto.getCodigoCartao(),
                        dto.getParcelas());
                credito = new PedidoCredito(
                        dto.getNumeroCartao(),
                        dto.getCodigoCartao(),
                        dto.getParcelas());
                break;
            case DEBITO:
                pedido = new PedidoDebito(
                        dto.getNumeroCartao(),
                        dto.getCodigoCartao());
                break;
            case PIX:
                pedido = new PedidoPix();

                break;
            default:
                throw new IllegalArgumentException("Forma de pagamento não reconhecida");
        }

        // Mapeia os demais campos
        modelMapper.map(dto, pedido);
        pedido = pedidosRepository.save(pedido);
        pedido.setEmail(email);
        pedido.setUuid(uuid);
        pedido.setDataCadastro(LocalDateTime.now());
        pedidosRepository.save(pedido);
        enviarPedidoParaEmail(pedido);

        PedidosUserDTO respostaDTO = modelMapper.map(pedido, PedidosUserDTO.class);

        if (pedido instanceof PedidoPix) {
            respostaDTO.setChavePix(PedidoPix.CHAVE_PIX);
        }

        return respostaDTO;
    }

    public Optional<PedidosDTO> buscaPorId(long id) {

        Optional<Pedido> busca = pedidosRepository.findById(id);

        return busca.map(pedido -> modelMapper.map(pedido, PedidosDTO.class));
        //return Optional.ofNullable(modelMapper.map(busca, PedidosDTO.class));
    }

    public void deletarPorId(Long id) {

            pedidosRepository.deleteById(id);
       }

    public List<PedidosDTO> buscarPedidos() {
        List<Pedido> pedidos = pedidosRepository.findAll();

        return pedidos.stream()
                .map(pedido -> new PedidosDTO(pedido))
                .collect(Collectors.toList());
    }
    @Transactional
    public ResponseEntity<PedidosDTO> atualizarRespostaPedido(ConfirmPaymentDTO dto) {
        Optional<Pedido> pedidoOptional = pedidosRepository.findById(dto.getPaymentId());

        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();

            pedido.setPaymentStatus(dto.getPaymentStatus());
            pedido.setDataProcessamento(dto.getPaymentDate());

            Pedido pedidoAtualizado = pedidosRepository.save(pedido);

            PedidosDTO resposta = new PedidosDTO(pedidoAtualizado);

            return ResponseEntity.ok(resposta);
        } else {
            // Se o pedido não for encontrado, lança uma exceção
            throw new IllegalArgumentException("Pedido não encontrado!");
        }
    }


//  ---------  CONVERSÃO E ENVIO PARA PAYMENT E EMAIL  ------------

    public PaymentDTO converterParaPaymentDTO(PedidosDTO pedido){

        return  modelMapper.map(pedido, PaymentDTO.class);
    }

    public void enviarPedidoParaPayment(PedidosDTO pedido){
        PaymentDTO paymentDTO = converterParaPaymentDTO(pedido);

        rabbitTemplate.convertAndSend("pedido.cadastro", paymentDTO);
    }

    public EmailDTO converterParaEmailDTO(PedidosDTO pedido){

        return modelMapper.map(pedido, EmailDTO.class);

    }

    private void enviarPedidoParaEmailD(Pedido pedido){
        EmailDTO emailDTO = new EmailDTO(pedido);
        rabbitTemplate.convertAndSend("pedido.email-notificacao", emailDTO);
    }
    private void enviarPedidoParaEmailC(Pedido pedido, PedidoCredito credito){
        EmailDTO emailDTO = new EmailDTO(pedido, credito);
        rabbitTemplate.convertAndSend("pedido.email-notificacao", emailDTO);
    }
    public void enviarPedidoParaEmail(Pedido pedido){
        Optional<PedidoCredito> credito = creditoRepository.findById(pedido.getId());
        EmailDTO emailDTO = new EmailDTO(pedido, credito.orElse(null));
        rabbitTemplate.convertAndSend("pedido.email-notificacao", emailDTO);
    }
    public Pedido findPedido(Long id){
        Optional<Pedido> pedido = pedidosRepository.findById(id);

        return pedido.get();
    }

}
