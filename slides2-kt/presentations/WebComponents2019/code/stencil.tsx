import {Component, Prop} from '@stencil/core';

@Component({
    tag: 'my-first-component',
    styleUrl: 'my-first-component.scss'
})
export class MyComponent {
    // Indicate that name should be
    // a public property on the component
    @Prop() name: string;

    render() {
        // JSX
        return (<p>My name is {this.name}</p>);
    }
}