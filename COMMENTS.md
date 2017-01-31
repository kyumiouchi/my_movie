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
4. Resolu��o mantida para os devices de todas as resolu��es
5. Teste de Interface
6. Salvar itens

#### Como foi feito
1.1. Usei o RecycleView para inflar os itens ap�s o download da json.
1.2. Poss�vel adicionar mais itens na lista
2. Ap�s adicionar um filme, o usu�rio � direcionado para a tela de detalhes que ir� acessar as informa��es do filmes. Os filmes s� podem ser adicionados se j� existirem.
2.1. Poss�vel excluir o item e um Dialog ir� ser mostrado para o usu�rio concluir ou n�o. Ao excluir o item apaga do database.
3. Pode adicionar filmes novos e caso n�o exista ele mostrar� o popup.
4. A classe DisplayLayout � respons�vel em manter todos os componentes proporcionais (os textos, imagens, Views,...). Logo posso executar a aplica��o em mdpi, hdpi, xhdpi, xxhdpi e xxxhdpi, e ser� redimencionada.
5. Utilizei o Espresso para executar teste de Interface.
6. Os itens s�o salvos pelo SQLite usando a API OMRLite.

#### Porque foi feito
1.1. Utilizei o RecycleView que tem uma performace melhorada em rela��o ao ListView/GridView. RecycleView pode, por exemplo, reutilizar o mesmo item criado.
1.2. Utilizei o Retrofit como REST Client, pois � uma API conhecida e como a aplica��o � pequena e s� usa comunica��o REST para somente leitura, n�o havia necessidade de colocar uma implementa��o mais robusta. Assim como, o retrofit � uma API que fornece uma comunica��o r�pida e segura.
Utilizei o Gson para facilitar a leitura do Json e transformar suas informa��o em modelo de objeto.
2. Para excluir � acessado o banco e objeto � deletado.
3. Tela criada para adicionar novos filmes.
4. Utilizei uma classe customizada para n�o precisar utilizar dimens para cada resolu��o de tela.
5. Utilizei o Espresso, pois � API nativa do android, fac�l de usar e atende as necessidades do projeto.
6. Como � necess�rio o CRUD achei melhor usar DataBase. SharedPreference � usado apenas para tipos primitivos e a lista n�o precisa ser salva em disco.

#### O que n�o foi feito e o motivo
-Editar os filmes, seria necess�rio muitas verifica��es e requer um cuidado maior para um curto tempo
-Adicionar itens que n�o existe na lista. Achei desnecess�rio j� que n�o posso editar.

#### Melhorias adicionadas no projeto
-A classe CustomLog � utilizado para Log de Debug para futuramente n�o precisar retirar cada Log do app.
-A classe Fonts � utilizado para curstomiza��o da fonte da aplica��o.
-A API UniversalImageLoader � utilizado para gerenciar o download de imagens e o armazenar em cache enviada do Json, de forma que evite problemas de OutOfMemory ou performace ruim para usu�rio. Existem outras APIs como Picasso, Glide e Fresco, mas optei por UniversalImageLoader pelo motivos mencionado acima.
-A classe RecyclerViewMatcher � uma classe criada a partir do Matcher para facilitar e validar os testes utilizando o RecyclerView. e 

