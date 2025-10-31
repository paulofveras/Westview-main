-- 1. Dados independentes (não dependem de outras tabelas)
INSERT INTO telefone (codigoArea, numero) VALUES ('63','888888888'); -- ID 1
INSERT INTO telefone (codigoArea, numero) VALUES ('61','333333333'); -- ID 2
INSERT INTO telefone (codigoArea, numero) VALUES ('62','222222222'); -- ID 3
INSERT INTO telefone (codigoArea, numero) VALUES ('11','111111111'); -- ID 4
INSERT INTO telefone (codigoArea, numero) VALUES ('63', '987654321'); -- ID 5
INSERT INTO telefone (codigoArea, numero) VALUES ('11', '33334444');  -- ID 6

INSERT INTO endereco (cep, rua, numero) VALUES (11111111,'Rua 1',12); -- ID 1
INSERT INTO endereco (cep, rua, numero) VALUES (10101010,'Rua 10',2); -- ID 2
INSERT INTO endereco (cep, rua, numero) VALUES (55555555,'Rua 5',14); -- ID 3
INSERT INTO endereco (cep, rua, numero) VALUES (88888888,'Rua Tauá',8); -- ID 4
INSERT INTO endereco (cep, rua, numero) VALUES (77777777, 'Avenida dos Quadrinhos', 123); -- ID 5
INSERT INTO endereco (cep, rua, numero) VALUES (66666666, 'Alameda dos Editores', 456);  -- ID 6

-- 2. Usuários (necessários para Cliente e Funcionário)
-- Senha para todos é "123"
INSERT INTO usuario (username, senha) VALUES ('joao123', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA=='); -- ID 1
INSERT INTO usuario (username, senha) VALUES ('visao', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==');   -- ID 2
INSERT INTO usuario (username, senha) VALUES ('billy', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==');   -- ID 3

-- 3. Inserindo as Pessoas (base para Cliente, Funcionário, Fornecedor)
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('João', 'joao@gmail.com', 1, 1);
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('Visao', 'visao@gmail.com', 2, 2);
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('Billy', 'billy@gmail.com', 3, 3);
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('Panini Brasil Ltda', 'panini@gmail.com', 5, 5);
INSERT INTO pessoa (nome, email, id_endereco, id_telefone) VALUES ('NewJeans Store', 'newjeans@gmail.com', 6, 6);

-- 4. Inserindo os dados específicos de PessoaFisica
INSERT INTO pessoafisica (id, cpf) VALUES (1, '11111111111'); -- João
INSERT INTO pessoafisica (id, cpf) VALUES (2, '22222222222'); -- Visao
INSERT INTO pessoafisica (id, cpf) VALUES (3, '33333333333'); -- Billy

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

-- 7. Quadrinhos
INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) VALUES ('Secret Wars','Marvel Comics',40.50,320,1,4,10,'secret-wars.jpg');
INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) VALUES ('X-men','Marvel Comics',50.50,360,2,4,10,'x-men.jpg');
INSERT INTO quadrinho (nome, descricao, preco, quantPaginas, material, id_fornecedor, estoque, nomeImagem) VALUES ('Get Up','NewJeans Bunny Beach Bag',150.00,84,4,5,50,'get-up.jpg');

-- 8. Favoritos
INSERT INTO cliente_favoritos_quadrinho (id_cliente, id_quadrinho) VALUES (2, 1);
INSERT INTO cliente_favoritos_quadrinho (id_cliente, id_quadrinho) VALUES (3, 2);
INSERT INTO cliente_favoritos_quadrinho (id_cliente, id_quadrinho) VALUES (2, 3);
