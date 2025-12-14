# CalculadoraSaude

## 1. Introdução

O **CalculadoraSaude** é um aplicativo Android desenvolvido para fins educacionais, de forma a se cumprir os requisitos do trabalho **IMC e outras métricas de saúde** da disciplina de **Programação para Dispositivos móveis**. Neste presente trabalho, tem-se como principal objetivo a implementação de diferentes cálculos de indicadores básicos de saúde em Kotlin, utilizando Jetpack Compose e arquitetura MVVM.
Vale ressaltar que os resultados apresentados pelo aplicativo são **estimativas** e **não substituem avaliação médica ou nutricional profissional**.

---

## 2. Objetivos

### 2.1 Objetivo Geral

Desenvolver um aplicativo Android capaz de calcular indicadores de saúde a partir de dados fornecidos pelo usuário, utilizando Kotlin e Jetpack Compose.

### 2.2 Objetivos Específicos

* Implementar o cálculo do **Índice de Massa Corporal (IMC)**;
* Implementar o cálculo do **Peso Ideal**, sendo utilizada a fórmula de **Devine**;
* Implementar o cálculo da **Taxa Metabólica Basal (TMB)**, no qual foi considerado  equação de **Harris–Benedict**;
* Aplicar arquitetura Model-View-ViewModel (MVVM) na organização do projeto.

---

## 3. Tecnologias Utilizadas

* **Linguagem:** Kotlin
* **IDE:** Android Studio
* **UI Toolkit:** Jetpack Compose
* **Persistência de dados:** Room
* **Arquitetura:** MVVM
* **Sistema de Build:** Gradle

---

## 4. Estrutura do Projeto

O projeto segue a estrutura padrão recomendada para aplicações Android:

```
CalculadoraSaude/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── gradle/
├── build.gradle
└── README.md
```

Para as camadas de apresentação, tem-se

```
ui.theme/
 ├── components/
 ├── feature/
 ├── Color.kt
 ├── Theme.kt
 ├── Type.kt
MainActivity.kt
```

A representação lógica dos dados está no pacote de domain

```
domain/
 └── History.kt
```

A persistência de dados utilizando Room e a abstração de acesso aos dados se encontra no pacote data

```
data/
 ├── CalculationsDatabase.kt
 ├── HistoryDao.kt
 ├── HistoryEntity.kt
 ├── HistoryRepository.kt
 └── HistoryRepositoryImpl.kt
```

O gerenciamento de navegação entre telas está contido no pacote navigation:

```
navigation/
 └── CalculationsNavHost.kt
```

Ressalta-se que o pacote `components` contém componentes reutilizáveis da interface, enquanto o pacote `feature` agrupa funcionalidades organizadas por tela (activity).
Sendo assim, dentro do pacote de uma Activity, tem-se os arquivos de ActivityScreen (representando a view da tela), ActivityViewModel, assim como ActivityEvent. 


---

## 5. Funcionalidades Implementadas

* Entrada de dados como peso, altura, idade e sexo para realização dos cálculos. Ressalta-se a importância da inserção de valores válidos para a realização dos cálculos. Entradas inválidas são sinalizadas visualmente na interface por meio de alteração de coloração dos campos;
* Cálculo automático do IMC;
* Classificação do IMC conforme padrões da Organização Mundial da Saúde (OMS);
* Cálculo do peso ideal segundo a fórmula de Devine;
* Cálculo da taxa metabólica basal segundo Harris–Benedict;
* Exibição clara e objetiva dos resultados.
* Visualização de histórico de cálculos realizados anteriormente com descrições.

---

## 6. Fundamentação Teórica e Fórmulas

### 6.1 Índice de Massa Corporal (IMC)

O IMC é um indicador utilizado para avaliar a relação entre peso e altura de um indivíduo, sendo definido por:

IMC = peso (kg) / altura² (m)

A classificação do IMC segue os critérios estabelecidos pela OMS.

---

### 6.2 Peso Ideal – Fórmula de Devine (1974)

A fórmula de Devine estima o peso ideal a partir da altura e do sexo do indivíduo.
Para este indicador tem-se o peso em quilogramas (kg) e a altura previamente fornecida em centímetros convertida para polegadas.

**Homens:**

Peso = 50,0 + 2,3 * ((altura/2,54) - 60)


**Mulheres:**

Peso = 45,5 + 2,3*((altura/2,54) - 60)


Onde:

* 60 polegadas = 152,4 cm
* 1 polegada = 2,54 cm

---

### 6.3 Taxa Metabólica Basal – Harris–Benedict (Equação Revisada)

A Taxa Metabólica Basal representa a quantidade mínima de energia necessária para manter as funções vitais do organismo em repouso.

**Homens:**

TMB = 88,362 + (13,397 * peso) + (4,799 * altura) - (5,677 * idade)


**Mulheres:**

TMB = 447,593 + (9,247 * peso) + (3,098 * altura) - (4,330 * idade)


O resultado é expresso em **kcal/dia**.

---

### 6.4 Calorias diárias

A partir do valor da TMB, o aplicativo estima o **Gasto Energético Total (GET)**, que corresponde à quantidade aproximada de calorias necessárias diariamente para a manutenção do peso corporal, considerando o nível de atividade física do indivíduo.
Este indice pode ser calculado de acordo com a fórmula abaixo:

Gasto Calórico Diário Total = TMB × Fator de Atividade

Os fatores de atividade considerados são:

| Nível de atividade | Fator |
|-------------------|-------|
| Sedentário | 1,2 |
| Levemente ativo | 1,375 |
| Moderadamente ativo | 1,55 |
| Muito ativo | 1,725 |

---

## 7. Metodologia de Desenvolvimento

O aplicativo foi desenvolvido utilizando uma arquitetura MVVM, sendo que as responsabilidades foram divididas (visualização, cálculos, persistência de dados, entre outros).
Vale ressaltar que para persistência de dados foi utilizado a biblioteca Room, permitindo o armazenamento local do histórico de cálculos realizados pelo usuário.


---

## 8. Execução do Projeto

### 8.1 Pré-requisitos

* Android Studio instalado;
* Android SDK configurado;
* Emulador Android ou dispositivo físico compatível.

### 8.2 Passos para Execução

```bash
git clone https://github.com/EduardooMarson/CalculadoraSaude.git
```

1. Abrir o projeto no Android Studio;
2. Aguardar a sincronização do Gradle;
3. Executar o aplicativo em um emulador ou dispositivo físico.



**Projeto desenvolvido para fins educacionais.**
