# Minha Estante 📚

Aplicativo Android para organizar leituras pessoais. Na estante você vê seus livros, filtra por
status de leitura (**Quero ler**, **Lendo**, **Lido**), abre os detalhes de cada livro e atualiza
em que ponto da leitura você está.

> **Entrega parcial:** Android Views (layouts XML), navegação com `Intent` explícita e dados
> simulados (mocks). A próxima etapa (Jetpack Compose) vai evoluir o mesmo tema.

## Objetivo

Ajudar leitores a acompanhar o que querem ler, o que estão lendo e o que já leram, reunindo
todas as funcionalidades em torno do mesmo problema: **organizar a própria lista de leitura**.

## Fluxo do aplicativo

1. **Estante** (`MainActivity`): lista de livros em um `RecyclerView`, com capa, autor, ano,
   páginas, status e nota. Os chips no topo filtram a lista por status e o resumo mostra
   quantos livros aparecem. Se um filtro não tiver livros, aparece um estado vazio.
2. Ao tocar em um livro, uma **Intent explícita** abre a `BookDetailActivity`, levando o
   **id do livro** como extra.
3. **Detalhes** (`BookDetailActivity` + `BookDetailFragment`): mostra título, subtítulo,
   autor, ano, páginas, editora, avaliação, gêneros e sinopse. Quando alguma informação não
   existe, aparece "Não informado" ou o campo fica oculto. O seletor de status atualiza a
   interface na hora.
4. Ao voltar, a tela de detalhes **devolve o resultado** (id, status anterior e novo status).
   A estante se atualiza e mostra uma `Snackbar` com a opção **Desfazer**.

## Como rodar o projeto

### Pré-requisitos

- **Android Studio Otter 3 Feature Drop (2025.2.3) ou mais recente.** O projeto usa o
  Android Gradle Plugin 9.0.1, compatível do Otter 3 Feature Drop até o Quail 4 (2026.1.4).
- **Android SDK Platform 36.** Se estiver faltando, o Android Studio oferece a instalação
  durante a sincronização.
- Emulador ou aparelho com **Android 8.0 (API 26) ou superior**.
- JDK: o JDK que já vem com o Android Studio (JBR) é suficiente. Não há configuração extra.

### Passo a passo (Android Studio)

1. Clone o repositório:
   ```bash
   git clone <url-deste-repositorio>
   ```
2. No Android Studio, use **File › Open** e selecione a pasta do projeto.
3. Aguarde o **Gradle Sync** terminar. As dependências são baixadas automaticamente.
4. Escolha a configuração **app** e um emulador ou aparelho, depois clique em **Run ▶**.

### Pela linha de comando

```bash
./gradlew assembleDebug
```

```bash
./gradlew testDebugUnitTest
```

```bash
./gradlew installDebug
```

- `assembleDebug` gera o APK em `app/build/outputs/apk/debug/app-debug.apk`.
- `testDebugUnitTest` executa os testes unitários.
- `installDebug` instala o app no emulador ou aparelho conectado.

No Windows, use `gradlew.bat` no lugar de `./gradlew`. Na linha de comando, o Gradle
precisa de um JDK 17 ou superior (por exemplo, o JBR do Android Studio em `JAVA_HOME`).

### Segredos

O projeto **não usa chaves de API nem senhas**, então nenhum arquivo `.env` é necessário.
O `.gitignore` já ignora `.env`, keystores e o `local.properties` (que guarda o caminho
do SDK de cada máquina).

## Bibliotecas externas

| Biblioteca | Para que é usada |
|---|---|
| **AndroidX Core KTX** | Extensões Kotlin para Views (`isVisible`, `updatePadding`, `doOnAttach`) e tratamento de *window insets* com `WindowInsetsCompat`. |
| **AndroidX AppCompat** | `AppCompatActivity` e compatibilidade de temas e Views com versões antigas do Android. |
| **AndroidX Activity KTX** | `enableEdgeToEdge()` e a Activity Result API (`registerForActivityResult` e um `ActivityResultContract` próprio). |
| **AndroidX Fragment KTX** | `Fragment`, `FragmentContainerView`, transações com `commit { }` e Fragment Result API para o Fragment falar com a Activity. |
| **AndroidX RecyclerView** | Lista de livros com `ListAdapter` e `DiffUtil`, que atualiza só os itens alterados. |
| **Material Components** | Tema Material 3 claro/escuro, `MaterialToolbar`, `MaterialCardView`, `Chip`/`ChipGroup`, `MaterialButtonToggleGroup` e `Snackbar`. |
| **JUnit 4** (só testes) | Testes unitários do repositório e dos modelos. |

Ferramentas de build: Android Gradle Plugin 9.0.1, que já compila Kotlin sem o plugin
`kotlin-android`, e Gradle 9.1 via *wrapper*. As versões estão centralizadas em
`gradle/libs.versions.toml`.

## Estrutura do código

```
app/src/main/java/com/thales/minhaestante/
├── AppContainer.kt                  # injeção de dependências manual (repositório compartilhado)
├── data/
│   ├── BookRepository.kt            # fonte única dos dados (em memória nesta etapa)
│   ├── mock/MockBooks.kt            # dados simulados
│   └── model/
│       ├── Book.kt                  # data class imutável, com campos opcionais
│       └── ReadingStatus.kt         # enum de status de leitura
└── ui/
    ├── common/                      # formatação e cores compartilhadas, window insets
    ├── components/
    │   └── ReadingStatusSelectorView.kt   # componente XML reutilizável
    ├── list/
    │   ├── MainActivity.kt          # tela 1: estante
    │   └── BookAdapter.kt           # adapter do RecyclerView
    └── detail/
        ├── BookDetailActivity.kt    # tela 2: recebe a Intent e hospeda o Fragment
        ├── BookDetailFragment.kt    # conteúdo dos detalhes (ciclo de vida + ViewBinding)
        └── OpenBookDetailContract.kt# Intent explícita de ida e resultado de volta, tipados

app/src/main/res/layout/
├── activity_main.xml                # LinearLayout, HorizontalScrollView, ChipGroup, FrameLayout, RecyclerView
├── item_book.xml                    # item da lista (MaterialCardView, FrameLayout, ImageView, Space)
├── view_empty_state.xml             # estado vazio, incluído com <include>
├── activity_book_detail.xml         # Toolbar + FragmentContainerView
├── fragment_book_detail.xml         # NestedScrollView com as informações do livro
├── view_info_item.xml               # par "rótulo + valor", incluído 3 vezes
├── item_genre_chip.xml              # chip inflado em código para cada gênero
└── view_reading_status_selector.xml # layout (<merge>) do componente de status
```

## Requisitos atendidos

### Obrigatórios

| Requisito | Onde |
|---|---|
| Duas telas em Views/XML com Views e ViewGroups adequados | `activity_main.xml` e `item_book.xml` (lista); `activity_book_detail.xml` e `fragment_book_detail.xml` (detalhes). Usam `TextView`, `ImageView`, `Space`, `RatingBar`, `LinearLayout`, `FrameLayout`, `RecyclerView`, `NestedScrollView` e `ChipGroup`. |
| Navegação por `Intent` explícita, com passagem de dados | `BookDetailActivity.newIntent()` cria `Intent(context, BookDetailActivity::class.java)` com o id do livro. O resultado volta com id, status anterior e status novo (`OpenBookDetailContract`). |
| Views conectadas ao Kotlin e interação que atualiza a interface | ViewBinding em todas as telas. Os chips de filtro atualizam a lista; o toque no item abre os detalhes; o seletor de status atualiza a dica e o resultado; a Snackbar tem "Desfazer". |
| Modelos imutáveis (`data class`) e valores opcionais | `Book` só tem `val`, e alterações usam `copy()`. Os campos `subtitle`, `publisher`, `publicationYear`, `pageCount`, `synopsis` e `rating` são anuláveis e tratados com `?.`, `?:`, `listOfNotNull` e `isVisible`. Extras ausentes ou inválidos na Intent também são tratados. |
| Dados simulados | `MockBooks.kt`. Alguns livros não têm certas informações de propósito. |

### Opcionais

| Item | Onde |
|---|---|
| Apenas ViewBinding (nenhum `findViewById`) | `buildFeatures { viewBinding = true }` e as classes `*Binding` em todas as telas, itens e componentes. |
| Componentes XML reutilizáveis, inflados quando necessário, com eventos | `ReadingStatusSelectorView` infla `view_reading_status_selector.xml` e expõe `onStatusChanged`. `view_info_item.xml` e `view_empty_state.xml` entram com `<include>`. `item_genre_chip.xml` é inflado uma vez por gênero. |
| Interface funcional em `Fragment`, com ciclo de vida e ViewBinding | `BookDetailFragment` cria o binding em `onCreateView`, configura a tela em `onViewCreated` e libera o binding em `onDestroyView`. Recebe argumentos por `newInstance()` e avisa a Activity pela Fragment Result API. |

## Decisões técnicas

- **Só o id vai na Intent.** A tela de detalhes busca o livro no repositório, que é a fonte
  única dos dados. Assim ela sempre mostra a versão atual do livro e não é preciso
  serializar o objeto inteiro (`Parcelable`).
- **Contrato de resultado tipado** (`OpenBookDetailContract`) em vez do
  `startActivityForResult`, que está depreciado. A lista recebe um
  `StatusChange?`: `null` significa que nada mudou.
- **Repositório + `AppContainer`.** As telas não conhecem os mocks, só o repositório. Na
  Etapa 2 dá para trocar a fonte de dados (API, Room) sem mexer nas telas.
- **Imutabilidade.** O repositório guarda uma `List` somente-leitura. Para alterar um
  livro, cria-se uma nova lista com a cópia (`copy`) atualizada. O `init` da data class
  valida o modelo, por exemplo a nota entre 0 e 5.
- **Rotação de tela.** O filtro selecionado é salvo em `onSaveInstanceState`. Nos detalhes,
  o status inicial é salvo para recalcular o resultado, porque o resultado de uma Activity
  não sobrevive à recriação. O `FragmentManager` recria o Fragment sozinho.
- **Edge-to-edge.** Com `targetSdk` 35 ou maior, o Android desenha o app atrás das barras
  do sistema. `applySystemBarsPadding()` aplica os *insets* para nada ficar escondido.
- **Acessibilidade.** Imagens decorativas ficam ocultas do leitor de tela, títulos de seção
  são marcados como *headings* e a nota tem descrição falada. Os textos usam `sp`, os alvos
  de toque têm 48dp e o resumo e a dica de status são *live regions*. As cores Material 3
  foram definidas para os temas claro e escuro.

### Limitações desta etapa

Os dados ficam apenas em memória: as alterações de status valem enquanto o app estiver
aberto e voltam aos valores dos mocks quando o processo é encerrado. A persistência
(Room/DataStore) faz parte da Etapa 2.
