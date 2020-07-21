const express = require('express')
const app = express()
const port = 3000

let quotes = [
    "We interrupt this program to annoy you and make things generally more irritating",
    "Strange women lying in ponds, distributing swords, is no basis for a system of government!",
    "We use only the finest baby frogs; dew picked and flown from Iraq",
    "There's nothing wrong with you that an expensive operation can't prolong",
    "It's just a flesh wound",
    "Are you suggesting that coconuts migrate?",
    "Nobody expects the Spanish Inquisition!"
];

function getRandomQuote() {
    let i = Math.floor(Math.random() * quotes.length);
    return quotes[i];
}

app.get('/', (req, res) => {
    res.send(getRandomQuote());
})

app.get('/json', (req, res) => {
    res.send({
        'quote': getRandomQuote()
    });
})

app.get('/redirect', (req, res) => {
    res.redirect('/json');
})

app.listen(port, () => console.log(`Example app listening at http://localhost:${port}`))

