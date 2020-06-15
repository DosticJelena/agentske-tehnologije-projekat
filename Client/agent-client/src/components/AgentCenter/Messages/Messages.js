import React from 'react';
import axios from 'axios';

class Messages extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            performatives: [],
            runningAgents: [],
            sender: null,
            receivers: [],
            performative: "NOT_UNDERSTOOD"
        }
    }

    setSender = (agent) => {
        console.log(agent);
        this.setState({ sender: agent });
    }

    setReceivers = (agent) => {
        let tempList = [...this.state.receivers];
        var index = tempList.indexOf(agent);
        if (index !== -1) {
            tempList.splice(index, 1);
            this.setState({ receivers: tempList });
        } else {
            this.setState({ receivers: [...this.state.receivers, agent] });
        }
    }

    setPerformative = e => {
        this.setState({performative: e.target.value});
    }

    socketFunc = () => {
        var urlReg = this.props.wsURL;
        var socketReg = new WebSocket(urlReg);
        socketReg.onmessage = (msg) => {
            console.log(socketReg.readyState + " ---- (message) ---- ");
            console.log(JSON.parse(msg.data));
            this.setState({ runningAgents: JSON.parse(msg.data) });
        }
        socketReg.onclose = () => socketReg = null;
    }

    getRunningAgents = () => {
        axios.get(this.props.masterURL + "/client/agents/running")
            .then(response => {
                console.log(response);
                this.setState({
                    runningAgents: response.data
                })
            })
            .catch(error => {
                console.log(error);
            })
    }

    sendMessage = () => {
        axios.post(this.props.masterURL + "/client/messages", {
            sender: this.state.sender,
            receivers: this.state.receivers,
            content: "https://www." + this.state.sender.name + "/",
            performative: this.state.performative
        })
            .then(response => {
                console.log(response);
                this.setState({performative: "NOT_UNDERSTOOD", sender: null, receivers: []})
            })
            .catch(error => {
                console.log(error);
            })
    }

    componentDidMount() {
        axios.get(this.props.masterURL + "/client/messages")
            .then(response => {
                console.log(response);
                this.setState({ performatives: response.data })
            })
            .catch(error => {
                console.log(error);
            });

        this.getRunningAgents();
        this.socketFunc();
    }

    render() {
        return (
            <div className="row">
                <div className="col-4">
                    <h2>Performatives</h2>
                    <hr />
                    <div className="perfs">
                        {this.state.performatives.map(p => <button className="perf-btn" value={p} onClick={this.setPerformative}>{p}</button>)}
                    </div>
                </div>
                <div className="col-8">
                    <h2>Create Message</h2>
                    <hr />
                    <div className="row">
                        <div className="col-2">
                            <div className="dropdown">
                                <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Sender
                                </button>
                                <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    {this.state.runningAgents.filter(agent => agent.type.type === "Master").map(agent => {
                                        return <button className="dropdown-item" value={agent.name} onClick={() => this.setSender(agent)}>{agent.name} | {agent.type.type}</button>
                                    })}
                                </div>
                            </div>
                        </div>
                        <div className="col-3">
                            {this.state.sender === null ? null : <div className="sender">{this.state.sender.name} | <em>{this.state.sender.type.type}</em></div>}
                        </div>
                        <div className="col-2">
                            <div className="dropdown">
                                <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Receivers
                                </button>
                                <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                    {this.state.runningAgents.filter(agent => agent.type.type !== "Master" && agent.name === this.state.sender?.name).map(agent =>  {
                                        return <label>
                                                <input onClick={() => this.setReceivers(agent)} value={agent.name + " (" + agent.host.address + ")"} type="checkbox" /> {agent.name} | {agent.type.type}
                                            </label>
                                    })}
                                </div>
                            </div>
                        </div>
                        <div className="col-3">
                            {this.state.receivers.map(agent =>
                                <div className="sender">{agent.name} | <em>{agent.type.type}</em></div>
                            )}
                        </div>
                    </div>
                    <hr />
                    <div className="row all-nodes">
                        <table>
                            <tr>
                                <td>Web Site</td><td>Performative</td>
                            </tr>
                            <tr>
                                {this.state.sender ? <td>https://www.{this.state.sender?.name}/</td> : <td>https://</td>}<td>{this.state.performative}</td>
                            </tr>
                        </table>
                    </div>
                    <button className="btn msg-btn" onClick={this.sendMessage}>Send</button>
                </div>
            </div>
        );

    }
}

export default Messages;