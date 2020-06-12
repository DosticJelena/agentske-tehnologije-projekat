import React from 'react';
import Nodes from './Nodes/Nodes';
import Agents from './Agents/Agents';
import Messages from './Messages/Messages';

class AgentCenter extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            masterURL: "http://192.168.0.20:8080/WarAT2020/rest",
            wsURL: "ws://192.168.0.20:8080/WarAT2020/ws",
            showNodes: true,
            showAgents: false,
            showMessages: false
        }
    }

    showNodesFunction = () => {
        this.setState({ showAgents: false, showNodes: true, showMessages: false });
    }

    showAgentsFunction = () => {
        this.setState({ showAgents: true, showNodes: false, showMessages: false });
    }

    showMessagesFunction = () => {
        this.setState({ showAgents: false, showNodes: false, showMessages: true });
    }

    render() {
        return (
            <div className="AgentCenter">
                <h1>Agent Center
                    <button
                        onClick={this.showNodesFunction}
                        className={this.state.showNodes ? "btn show-hide-btn active-btn" : "btn show-hide-btn"}>
                        Nodes
                    </button>
                    <button
                        onClick={this.showAgentsFunction}
                        className={this.state.showAgents ? "btn show-hide-btn active-btn" : "btn show-hide-btn"}>
                        Agents
                    </button>
                    <button
                        onClick={this.showMessagesFunction}
                        className={this.state.showMessages ? "btn show-hide-btn active-btn" : "btn show-hide-btn"}>
                        Messages
                    </button>
                </h1>
                <hr />
                <br />
                {this.state.showNodes ?
                    <Nodes masterURL={this.state.masterURL}/>
                    : null}
                {this.state.showAgents ?
                    <Agents wsURL={this.state.wsURL} masterURL={this.state.masterURL}/>
                    : null}
                {this.state.showMessages ?
                    <Messages masterURL={this.state.masterURL}/>
                    : null}
            </div>
        );
    }

}

export default AgentCenter;