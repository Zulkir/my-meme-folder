import React from "react";
import "./Folder.css";
import axios from 'axios';

export default class Folder extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            collapsed: false,
            editMode: false,
            editName: ""
        }
    }

    addNew = () => {
        const username = this.props.username;
        const fullPath = this.props.fullPath;
        const newName = "New Folder";
        axios.post(`/api/folder/${username}/structure?path=${fullPath}&newName=${newName}`)
            .then(res => {
                if (res.status === 200) {
                    this.props.onUpdate(res.data);
                }
            })
            .catch(e => {
                console.log(e);
            });
    }

    edit = () => {
        this.setState({
            editMode: true,
            editName: this.props.folder.name
        });
    }

    editDone = () => {
        this.setState({editMode: false});
        const username = this.props.username;
        const fullPath = this.props.fullPath;
        const newName = this.state.editName;
        axios.put(`/api/folder/${username}/structure?path=${fullPath}&newName=${newName}`)
            .then(res => {
                if (res.status === 200) {
                    this.props.onUpdate(res.data);
                }
            })
            .catch(e => {
                console.log(e);
            });
    }

    delete = () => {
        const username = this.props.username;
        const fullPath = this.props.fullPath;
        axios.delete(`/api/folder/${username}/structure?path=${fullPath}`)
            .then(res => {
                if (res.status === 200) {
                    this.props.onUpdate(res.data);
                }
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const fulLPath = this.props.fullPath;
        const isClickable = this.props.folder.children.length > 0;
        const isSelected = this.props.currentPath === fulLPath;
        return (
            <div className="folder-wrapper">
                <table><tbody>
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
                        {
                            this.state.editMode ? (
                                <td>
                                    <input
                                        type="text"
                                        className="folder-header-edit"
                                        value={this.state.editName}
                                        onBlur={this.editDone}
                                        onChange={e => this.setState({editName: e.target.value})}
                                    />
                                </td>
                            ) : (
                                <td
                                    className={
                                        "folder-header" +
                                        (isSelected ? " selected" : "")
                                    }
                                    onClick={e => this.props.onSelect(fulLPath)}
                                >
                                    <span>{this.props.folder.name}</span>
                                </td>
                            )
                        }
                    </tr>
                    {
                        (isSelected && !this.state.editMode) ? (
                            <tr>
                                <td/>
                                <td className="folder-current-buttons">
                                    <span
                                        style={{backgroundColor: "green"}}
                                        onClick={this.addNew}
                                    >＋</span>
                                    <span
                                        style={{backgroundColor: "blue"}}
                                        onClick={this.edit}
                                    >✎</span>
                                    <span
                                        style={{backgroundColor: "red"}}
                                        onClick={this.delete}
                                    >✕</span>
                                </td>
                            </tr>
                        ) : (<React.Fragment/>)
                    }
                </tbody></table>
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
                                username={this.props.username}
                                currentPath={this.props.currentPath}
                                onSelect={this.props.onSelect}
                                onUpdate={this.props.onUpdate}
                            />
                        );
                    })}
                </div>
            </div>
        );
    }
}