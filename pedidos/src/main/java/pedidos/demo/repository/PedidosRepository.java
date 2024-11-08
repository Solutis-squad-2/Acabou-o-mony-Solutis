package pedidos.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pedidos.demo.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidosRepository extends JpaRepository<Pedido, Long> {


        List<Pedido> findByCpf(String cpf);
}
