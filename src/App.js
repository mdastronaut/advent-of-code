import "./App.css";
import raw from "raw.macro";

function App() {
  return (
    <div className="App">
      <p></p>
      <div></div>
    </div>
  );
}

// INPUT
let input = raw("./input.txt");
let arr = input.split("\r\n\r\n");

// HOST_NUMBERS
let host_numbers = arr[0].replaceAll(",", " ").split(" ").map(Number);

// CREATE_DEFAULT_EVALUATION_CARD (FALSE)
const createEvaluationCard = () => {
  let evaluationCard = [];
  for (let a = 0; a < 5; a++) {
    evaluationCard[a] = new Array(5);
    for (let b = 0; b < 5; b++) {
      evaluationCard[a][b] = false;
    }
  }
  return evaluationCard;
};

// BINGO_CARDS_FROM_INPUT
let bingo_cards = [];

arr.slice(1).forEach((element) => {
  mapInputToArrays(element);
});

function mapInputToArrays(card_src) {
  let str_arr = card_src.split("\r\n");
  let card = {
    numbers: [],
    truthTable: createEvaluationCard(),
  };
  for (let e = 0; e < str_arr.length; e++) {
    let numline = str_arr[e].trim().split(/\s+/).map(Number);
    card.numbers.push(numline);
  }

  bingo_cards.push(card);
}

let winnerPack = null;
let winnerCard = null;
let winnerNumber = -1;

console.log(host_numbers);
console.log(bingo_cards);

// game_driver
const play = () => {
  for (let i = 0; i < host_numbers.length; i++) {
    try {
      winnerPack = check_bingo_cards(host_numbers[i]);
      winnerCard = winnerPack[0];
      winnerNumber = winnerPack[1];
    } catch (e) {}
    if (winnerCard != null) break;
  }
  console.log("winnerCard is : ");
  console.log(winnerCard);
  console.log("winner number is : " + winnerNumber);

  let sum_of_unmarked = 0;
  for (let a = 0; a < winnerCard.truthTable.length; a++) {
    for (let b = 0; b < winnerCard.truthTable[0].length; b++) {
      if (winnerCard.truthTable[a][b] === false) {
        sum_of_unmarked += winnerCard.numbers[a][b];
      }
    }
  }
  console.log(sum_of_unmarked * winnerNumber);
};

// CARD_EVALUATION
const check_bingo_cards = (number) => {
  for (let a = 0; a < bingo_cards.length; a++) {
    let card = bingo_cards[a];
    let isWinner = single_card_evaluation(card, number);
    if (isWinner) {
      return [card, number];
    }
  }
  return null;
};

const single_card_evaluation = (card, number) => {
  for (let a = 0; a < card.numbers.length; a++) {
    for (let b = 0; b < card.numbers[0].length; b++) {
      if (number === card.numbers[a][b]) {
        setEvaluationCard(card, a, b);
      }
    }
  }
  return checkEvaluationCard(card);
};

const checkEvaluationCard = (card) => {
  // row_scan
  let trueInRow = 0;
  for (let a = 0; a < card.truthTable.length; a++) {
    for (let b = 0; b < card.truthTable[0].length; b++) {
      if (card.truthTable[a][b]) {
        trueInRow++;
      }
    }
    if (trueInRow === card.truthTable.length) {
      return true;
    }
    trueInRow = 0;
  }

  // column_scan
  let trueInColumn = 0;
  for (let a = 0; a < card.truthTable.length; a++) {
    for (let b = 0; b < card.truthTable[0].length; b++) {
      if (card.truthTable[b][a]) {
        trueInColumn++;
      }
    }
    if (trueInColumn === card.truthTable.length) {
      return true;
    }
    trueInColumn = 0;
  }
  return false;
};

const setEvaluationCard = (card, a, b) => {
  card.truthTable[a][b] = true;
};

// START_GAME
play();

export default App;
