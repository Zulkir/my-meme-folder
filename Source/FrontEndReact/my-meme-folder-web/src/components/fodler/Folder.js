import React from "react";
import "./Folder.css";

export default class Folder extends React.Component {
    constructor(props) {
        super(props);
        this.state = {collapsed: false}
    }

    render() {
        return (
            <div className="folder-wrapper">
                <div
                    className="folder-header"
                    onClick={e => this.setState({
                        collapsed: !this.state.collapsed
                    })}
                >
                    {this.props.folder.name}
                </div>
                <div
                    className="folder-children-container"
                    style={{display: this.state.collapsed ? 'none' : 'block'}}
                >{
                    this.props.folder.children.map(child => (
                        <Folder folder={child} />
                    ))}
                </div>
            </div>
        );
    }
}