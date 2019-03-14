import { /* ... */ } from 'lit-element';

@customElement('my-element')
class MyElement extends LitElement {

	@property({ type: String }) mood;

	static get styles() {
		return css`.mood { color: green; }`;
	}

	render() {
		return html`Web Components are 
			<span class="mood">${this.mood}</span>!`;
	}
}