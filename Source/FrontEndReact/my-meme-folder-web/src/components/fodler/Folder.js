import React from "react";
import "./Folder.css";

export default class Folder extends React.Component {
    constructor(props) {
        super(props);
        this.state = {collapsed: false}
    }

    render() {
        const fulLPath = this.props.fullPath;
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
                    this.props.folder.children.map(child => {
                        const childFullPath = `${fulLPath}/${child.name}`;
                        return (
                            <Folder folder={child} key={childFullPath} fullPath={childFullPath}/>
                        );
                    })}
                </div>
            </div>
        );
    }
}