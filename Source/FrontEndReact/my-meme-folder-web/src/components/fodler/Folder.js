import React from "react";
import "./Folder.css";

export default class Folder extends React.Component {
    constructor(props) {
        super(props);
        this.state = {collapsed: false}
    }

    render() {
        const fulLPath = this.props.fullPath;
        const isClickable = this.props.folder.children.length > 0;
        return (
            <div className="folder-wrapper">
                <table>
                    <tbody>
                        <tr>
                            <td>
                                <div
                                    className={
                                        "folder-button" +
                                        (isClickable ? " clickable" : "")
                                    }
                                    onClick={e => this.setState({
                                        collapsed: !this.state.collapsed
                                    })}
                                >
                                    <span>{isClickable ? this.state.collapsed ? "＋" : "－" : "○"}</span>
                                </div>
                            </td>
                            <td
                                className={
                                    "folder-header" +
                                    (this.props.isSelected ? " selected" : "")
                                }
                                onClick={e => this.props.onSelect(fulLPath)}
                            >
                                <span>{this.props.folder.name}</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div
                    className="folder-children-container"
                    style={{display: this.state.collapsed ? 'none' : 'block'}}
                >{
                    this.props.folder.children.map(child => {
                        const childFullPath = `${fulLPath}/${child.name}`;
                        return (
                            <Folder
                                folder={child}
                                key={childFullPath}
                                fullPath={childFullPath}
                                onSelect={this.props.onSelect}
                            />
                        );
                    })}
                </div>
            </div>
        );
    }
}