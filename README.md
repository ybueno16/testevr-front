# VRTeste Frontend

Este projeto é um sistema frontend desenvolvido em Java, utilizando o padrão MVC (Model-View-Controller), para gerenciar clientes, produtos, pedidos e vendas. O sistema possui uma interface gráfica (provavelmente Swing ou similar) e se comunica com APIs para operações de CRUD e controle de estoque.

## Estrutura do Projeto

```
app/
	src/
		main/
			java/
				com/example/vrteste/front/
					App.java                # Classe principal
					Menu.java, Menu.form    # Menu principal da aplicação
					Cliente/                # Módulo de clientes
						Controller/           # Lógica de controle de clientes
						Model/                # Modelos de dados de clientes
						View/                 # Telas de clientes
					Pedido/                 # Módulo de produtos/pedidos
						Controller/           # Lógica de controle de produtos
						Model/                # Modelos de dados de produtos
						View/                 # Telas de produtos
					Venda/                  # Módulo de vendas
						Controller/           # Lógica de controle de vendas
						Model/                # Modelos de dados de vendas
						View/                 # Telas de vendas
```

## Funcionalidades
- Cadastro, consulta e edição de clientes
- Cadastro, consulta e baixa de produtos
- Cadastro e gerenciamento de vendas
- Controle de estoque
- Interface gráfica amigável
- Comunicação com APIs REST para operações de dados

## Como Executar

1. **Pré-requisitos:**
	 - Java 8+
	 - Gradle (wrapper incluso)

2. **Compilar o projeto:**
	 ```sh
	 ./gradlew build
	 ```

3. **Executar a aplicação:**
	 ```sh
	 ./gradlew run
	 ```
	 Ou execute o JAR gerado em `app/build/libs/app.jar`:
	 ```sh
	 java -jar app/build/libs/app.jar
	 ```

## Estrutura dos Pacotes
- `Controller`: Classes responsáveis pela lógica de negócio e comunicação com APIs.
- `Model`: Representação dos dados e DTOs.
- `View`: Telas e formulários da interface gráfica.

## Testes
Os testes unitários estão localizados em `app/src/test/java/com/example/vrteste/front/AppTest.java`.

Execute os testes com:
```sh
./gradlew test
```

## Licença
Este projeto está licenciado sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.