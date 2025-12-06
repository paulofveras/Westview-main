-- 1. Dados independentes (não dependem de outras tabelas)
INSERT INTO telefone (codigoArea, numero) VALUES ('63','888888888'); -- ID 1
INSERT INTO telefone (codigoArea, numero) VALUES ('61','333333333'); -- ID 2
INSERT INTO telefone (codigoArea, numero) VALUES ('62','222222222'); -- ID 3
INSERT INTO telefone (codigoArea, numero) VALUES ('11','111111111'); -- ID 4
INSERT INTO telefone (codigoArea, numero) VALUES ('63', '987654321'); -- ID 5
INSERT INTO telefone (codigoArea, numero) VALUES ('11', '33334444');  -- ID 6
INSERT INTO telefone (codigoArea, numero) VALUES ('21', '55556666');  -- ID 7

INSERT INTO endereco (cep, rua, numero) VALUES (11111111,'Rua 1',12); -- ID 1
INSERT INTO endereco (cep, rua, numero) VALUES (10101010,'Rua 10',2); -- ID 2
INSERT INTO endereco (cep, rua, numero) VALUES (55555555,'Rua 5',14); -- ID 3
INSERT INTO endereco (cep, rua, numero) VALUES (88888888,'Rua Tauá',8); -- ID 4
INSERT INTO endereco (cep, rua, numero) VALUES (77777777, 'Avenida dos Quadrinhos', 123); -- ID 5
INSERT INTO endereco (cep, rua, numero) VALUES (66666666, 'Alameda dos Editores', 456);  -- ID 6
INSERT INTO endereco (cep, rua, numero) VALUES (99999999, 'Rua do Administrador', 100); -- ID 7

-- 2. Usuários (necessários para Cliente e Funcionário)
-- Senha para todos é "123"
INSERT INTO usuario (username, senha) VALUES ('joao123', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA=='); -- ID 1
INSERT INTO usuario (username, senha) VALUES ('visao', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==');   -- ID 2
INSERT INTO usuario (username, senha) VALUES ('billy', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==');   -- ID 3
INSERT INTO usuario (username, senha) VALUES ('wanda', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==');   -- ID 4

-- 3. Inserindo as Pessoas (base para Cliente, Funcionário, Fornecedor)
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('João', 'joao@gmail.com', 1, 1);
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('Visao', 'visao@gmail.com', 2, 2);
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('Billy', 'billy@gmail.com', 3, 3);
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('Panini Brasil Ltda', 'panini@gmail.com', 5, 5);
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('NewJeans Store', 'newjeans@gmail.com', 6, 6);
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('Wanda Maximoff', 'wanda@westview.com', 7, 7);

-- 4. Inserindo os dados específicos de PessoaFisica
INSERT INTO pessoafisica (id, cpf) VALUES (1, '11111111111'); -- João
INSERT INTO pessoafisica (id, cpf) VALUES (2, '22222222222'); -- Visao
INSERT INTO pessoafisica (id, cpf) VALUES (3, '33333333333'); -- Billy
INSERT INTO pessoafisica (id, cpf) VALUES (6, '44444444444'); -- Wanda

-- 5. Inserindo os dados específicos de PessoaJuridica
-- CORREÇÃO APLICADA AQUI: Adicionado o valor para CNPJ.
INSERT INTO pessoajuridica (id, cnpj, nomefantasia) VALUES (4, '58600111222233', 'Panini Comics'); -- Panini
INSERT INTO pessoajuridica (id, cnpj, nomefantasia) VALUES (5, '98765432000188', 'NewJeans');     -- NewJeans

-- 6. Populando as tabelas finais da hierarquia (Cliente, Funcionario, Fornecedor)
INSERT INTO funcionario (id, cargo, id_usuario) VALUES (1, 'Vendedor', 1);
INSERT INTO cliente (id, id_usuario) VALUES (2, 2);
INSERT INTO cliente (id, id_usuario) VALUES (3, 3);
INSERT INTO fornecedor (id) VALUES (4);
INSERT INTO fornecedor (id) VALUES (5);
INSERT INTO funcionario (id, cargo, id_usuario) VALUES (6, 'Administrador', 4);
INSERT INTO administrador (id, nivel_acesso) VALUES (6, 'SUPER');

-- 7. Quadrinhos
INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) VALUES ('Secret Wars','Marvel Comics',40.50,320,1,4,10,'secret-wars.jpg');
INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) VALUES ('X-men','Marvel Comics',50.50,360,2,4,10,'x-men.jpg');
INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) VALUES ('Get Up','NewJeans Bunny Beach Bag',150.00,84,4,5,50,'get-up.jpg');

-- 7. Quadrinhos (Foco: Marvel & Protagonistas Femininas)
-- IDs 1, 2 e 3 são mantidos na ordem para não quebrar os favoritos/pedidos já existentes
INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('Capitã Marvel: Mais Alto...','Carol Danvers assume o manto e voa mais alto do que nunca.', 59.90, 136, 1, 4, 15, 'captain-marvel.jpg');

INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('Viúva Negra: O Fio da Teia','Natasha Romanoff confronta seu passado sombrio na Sala Vermelha.', 45.50, 112, 2, 4, 20, 'black-widow.jpg');

INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('Ms. Marvel: Nada Normal','A origem de Kamala Khan, a nova protetora de Jersey City.', 39.90, 120, 2, 4, 50, 'ms-marvel.jpg');

-- Novos Títulos Adicionais (Fornecedor 4 = Panini/Marvel)
INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('Feiticeira Escarlate: O Caminho das Bruxas','Wanda Maximoff viaja o mundo resolvendo crimes mágicos.', 72.00, 180, 1, 4, 10, 'scarlet-witch.jpg');

INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('Poderosa Thor: A Deusa do Trovão','Jane Foster se torna digna e empunha o Mjolnir.', 65.00, 160, 1, 4, 12, 'jane-foster-thor.jpg');

INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('Mulher-Hulk: A Advogada','Jennifer Walters equilibra a vida no tribunal e nos Vingadores.', 52.90, 144, 2, 4, 25, 'she-hulk.jpg');

INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('Spider-Gwen: Ghost-Spider','Gwen Stacy de outra dimensão combate o crime com estilo.', 42.00, 100, 2, 4, 30, 'spider-gwen.jpg');

INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('Fênix Negra','A saga clássica e trágica de Jean Grey e o poder cósmico.', 89.90, 220, 1, 4, 8, 'dark-phoenix.jpg');

INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('Shuri: Em Busca de Wakanda','A princesa de Wakanda e gênio tecnológico em sua própria aventura.', 35.90, 128, 2, 4, 40, 'shuri.jpg');

INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) 
VALUES ('X-23: Inocência Perdida','A história de origem de Laura Kinney, o clone do Wolverine.', 48.00, 150, 2, 4, 18, 'x23.jpg');

-- 8. Favoritos
INSERT INTO cliente_favoritos_quadrinho (id_cliente, id_quadrinho) VALUES (2, 1);
INSERT INTO cliente_favoritos_quadrinho (id_cliente, id_quadrinho) VALUES (3, 2);
INSERT INTO cliente_favoritos_quadrinho (id_cliente, id_quadrinho) VALUES (2, 3);

-- 9. Pedidos de Exemplo
-- Pedido 1: Cliente Visao (ID 2), Pago via PIX (ID 1), Total R$ 91.00
INSERT INTO pedido (data, total, id_cliente, formapagamento, statuspagamento) 
VALUES ('2024-12-01 14:30:00', 91.00, 2, 1, 1);

-- Itens do Pedido 1 (2x Secret Wars)
INSERT INTO itempedido (preco, quantidade, desconto, id_quadrinho, id_pedido) 
VALUES (40.50, 2, 0.0, 1, 1);


-- Pedido 2: Cliente Billy (ID 3), Não Pago via Boleto (ID 2), Total R$ 50.50
INSERT INTO pedido (data, total, id_cliente, formapagamento, statuspagamento) 
VALUES ('2024-12-05 09:15:00', 50.50, 3, 2, 2);

-- Itens do Pedido 2 (1x X-men)
INSERT INTO itempedido (preco, quantidade, desconto, id_quadrinho, id_pedido) 
VALUES (50.50, 1, 0.0, 2, 2);
