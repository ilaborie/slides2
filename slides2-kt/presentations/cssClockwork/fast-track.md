Fast Track

# Step 1 - Le cadre

```css
box-shadow:
    .05em .05em .1em rgba(0, 0, 0, .5),
    inset 0 0 0 .025em white;
    inset .05em .05em .1em rgba(0, 0, 0, .5),
    inset .5em .5em 1em rgba(0, 0, 0, .1);
```

# Step 2 - Une aiguille

```css
border-bottom-left-radius: 100% 80%;
border-bottom-right-radius: 100% 80%;
border:.001em solid rgba(0, 0, 0, .5);
box-shadow: 0 0 .05em rgba(0, 0, 0, .5);
```

# Step 3 - La rotation

# Step 4 - Heure et minutes

# Step 5 - La trotteuse

```css
.pin::before,
.pin::after {
    position: absolute;
    display: block;
    content: '';
    background-color: inherit;
    border-radius: 50%;
}

.pin::before {
    width: calc(2 * var(--width));
    height: calc(2 * var(--width));
    top: calc(-1 * var(--width));
    left: calc(-.5 * var(--width));
}

.pin::after {
    width: 100%;
    height: calc(2 * var(--width));
    top: calc(-2 * var(--width));
}
```

# Step 6 - Les animations


# Step 7 - Les marqueurs

```css
/* Ticks */
.tick {
    background: gold;
    position: absolute;
    top: 50%;
    left: calc(50% - .01em);
    height: .4em;
    width: .02em;
    opacity: .5;
    transform-origin: top center;
    transform: rotate(calc(.5turn + 1turn / 12 * var(--count)));
}

.tick::before {
    position: absolute;
    display: block;
    font-size: 8%;
    top: calc(100% - .8em);
    transform: translateX(-40%) rotate(.5turn);
    text-align: center;

    content: 'W';
}
```
