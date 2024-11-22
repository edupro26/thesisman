# ThesisMan | Projecto de Construção de Sistemas de Software

## Descrição
Este projeto foi desenvolvido no âmbito da disciplina de Construção de Sistemas de Software da Licenciatura em Engenharia Informática da Faculdade de Ciências da Universidade de Lisboa.

# Run
O projeto pode ser executado usando o docker. Para isso, basta correr o script `run.sh` que se encontra na raiz do projeto.
O script utiliza o `docker-compose` para correr o projeto e fazer o build da imagem docker (`--build`).
```bash
docker compose up --build
```
Uma base de dados `PostgreSQL` é inicializada dentro do container.

## Interface Gráfica - GUI

### Desktop - JavaFX
O projeto dispõe de um interface gráfica para desktop que permite a interação com o sistema. A interface foi desenvolvida em `JavaFX`. Para iniciar a GUI deve correr o comando:

```bash
mvn clean javafx:run
```

### Web
O projeto dispõe também de uma interface gráfica para web. Esta utiliza SSR (Server Side Rendering) e foi desenvolvida com recurso ao `Thymeleaf`.
É disponibilizada através do container referido anteriormente. Para aceder à interface gráfica web, basta aceder ao endereço `localhost:8080` no browser.

## Utilizadores para Demo
### Professores
- amfonseca@ciencias.ulisboa.pt
- casim@ciencias.ulisboa.pt
- dfsoares@ciencias.ulisboa.pt
- pjangelo@ciencias.ulisboa.pt

### Alunos
- fc57551@alunos.fc.ul.pt
- fc54979@alunos.fc.ul.pt
- fc52026@alunos.fc.ul.pt
- fc56783@alunos.fc.ul.pt
- fc56784@alunos.fc.ul.pt
- fc56785@alunos.fc.ul.pt

Qualquer password é aceite no caso dos professores e alunos.

## Autores

Projeto realizado por:

- Eduardo Proença - 57551
- Manuel Barral - 52026
- Tiago Oliveira - 54979
