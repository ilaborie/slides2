Fast Track

# Step 1 - Le cadre
# Step 2 - Une aiguille
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

```javascript
const delay = 60 * (min + 60 * h) + sec;
document.body.style.setProperty("--delay", "" + delay);
```

# Step 7 - Les marqueurs

```css
/* Ticks */
.tick {
    background: teal;
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