# my_movie
Aplicativo simples para cadastra filmes

====================
#### O que foi feito
1. Tela Principal
1.1. Mostra a lista de filmes
1.2. Posso adicionar filmes do site omdb
2. Tela Detalhe do Filme - Mostra os filmes adicionados
2.1 Excluir filme
3. Tela Pesquisar novo filme
4. Resolução mantida para os devices de todas as resoluções
5. Teste de Interface
6. Salvar itens

#### Como foi feito
1.1. Usei o RecycleView para inflar os itens após o download da json.
1.2. Possível adicionar mais itens na lista
2. Após adicionar um filme, o usuário é direcionado para a tela de detalhes que irá acessar as informações do filmes. Os filmes só podem ser adicionados se já existirem.
2.1. Possível excluir o item e um Dialog irá ser mostrado para o usuário concluir ou não. Ao excluir o item apaga do database.
3. Pode adicionar filmes novos e caso não exista ele mostrará o popup.
4. A classe DisplayLayout é responsável em manter todos os componentes proporcionais (os textos, imagens, Views,...). Logo posso executar a aplicação em mdpi, hdpi, xhdpi, xxhdpi e xxxhdpi, e será redimencionada.
5. Utilizei o Espresso para executar teste de Interface.
6. Os itens são salvos pelo SQLite usando a API OMRLite.

#### Porque foi feito
1.1. Utilizei o RecycleView que tem uma performace melhorada em relação ao ListView/GridView. RecycleView pode, por exemplo, reutilizar o mesmo item criado.
1.2. Utilizei o Retrofit como REST Client, pois é uma API conhecida e como a aplicação é pequena e só usa comunicação REST para somente leitura, não havia necessidade de colocar uma implementação mais robusta. Assim como, o retrofit é uma API que fornece uma comunicação rápida e segura.
Utilizei o Gson para facilitar a leitura do Json e transformar suas informação em modelo de objeto.
2. Para excluir é acessado o banco e objeto é deletado.
3. Tela criada para adicionar novos filmes.
4. Utilizei uma classe customizada para não precisar utilizar dimens para cada resolução de tela.
5. Utilizei o Espresso, pois é API nativa do android, facíl de usar e atende as necessidades do projeto.
6. Como é necessário o CRUD achei melhor usar DataBase. SharedPreference é usado apenas para tipos primitivos e a lista não precisa ser salva em disco.

#### O que não foi feito e o motivo
-Editar os filmes, seria necessário muitas verificações e requer um cuidado maior para um curto tempo
-Adicionar itens que não existe na lista. Achei desnecessário já que não posso editar.

#### Melhorias adicionadas no projeto
-A classe CustomLog é utilizado para Log de Debug para futuramente não precisar retirar cada Log do app.
-A classe Fonts é utilizado para curstomização da fonte da aplicação.
-A API UniversalImageLoader é utilizado para gerenciar o download de imagens e o armazenar em cache enviada do Json, de forma que evite problemas de OutOfMemory ou performace ruim para usuário. Existem outras APIs como Picasso, Glide e Fresco, mas optei por UniversalImageLoader pelo motivos mencionado acima.
-A classe RecyclerViewMatcher é uma classe criada a partir do Matcher para facilitar e validar os testes utilizando o RecyclerView. e 

